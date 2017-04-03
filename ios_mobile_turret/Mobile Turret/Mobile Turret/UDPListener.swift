//
//  UDPListener.swift
//  Mobile Turret
//
//  Created by Len on 4/2/17.
//  Copyright © 2017 redspace. All rights reserved.
//

import UIKit
import Foundation
import CocoaAsyncSocket

class UDPListener:NSObject, GCDAsyncUdpSocketDelegate {
    var _socket: GCDAsyncUdpSocket?
    var socket: GCDAsyncUdpSocket? {
        get {
            if _socket == nil {
                let port = UInt16(14000)
                let sock = GCDAsyncUdpSocket(delegate: self, delegateQueue: DispatchQueue.main)
                
                do {
                    try sock.bind(toPort: port)
                    try sock.beginReceiving()
                } catch let err as NSError {
                    NSLog(err.localizedDescription)
                    sock.close()
                    return nil
                }
                _socket = sock
            }
            return _socket
        }
        set {
            _socket?.close()
            _socket = newValue
        }
    }
    
    func udpSocket(_ sock: GCDAsyncUdpSocket, didReceive data: Data, fromAddress address: Data, withFilterContext filterContext: Any?){
        guard let stringData = String(data: data, encoding: String.Encoding.utf8) else {
            NSLog("Data received, but cannot be converted to String")
            return
        }
        NSLog("Data received: \(stringData)")
    }
    
    func start(){
        socket?.isConnected()
    }
}
