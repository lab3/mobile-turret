import SpriteKit
import Alamofire

class GameScene: SKScene {
    let controlT = SKSpriteNode(imageNamed: "dpad_lr")
    let controlC = SKSpriteNode(imageNamed: "dpad_full")
    
    let controlCPercent = SKLabelNode(fontNamed: "Arial")
    let controlCPos = SKLabelNode(fontNamed: "Arial")
    let turretPercent = SKLabelNode(fontNamed: "Arial")
    
    let fireNormal = SKSpriteNode(imageNamed: "fire_button4")
    let firePressed = SKSpriteNode(imageNamed: "fire_button_pressed")
    
    var turret: ControlState? = nil
    var fire: ButtonState? = nil
    var circlePad: CirclePad? = nil
    
    override func didMove(to view: SKView) {
        backgroundColor = SKColor.white
        
        turret = ControlState(scene: self, sprite: controlT, vertical: false)
        circlePad = CirclePad(scene: self, sprite: controlC)
        fire = ButtonState(scene: self, buttonSprite: fireNormal, buttonPressedSprite: firePressed)
        
        controlC.position = CGPoint(x: size.width * 0.13, y: size.height * 0.2)
        controlC.size = CGSize(width: 200, height: 200)
        
        controlT.position = CGPoint(x: size.width * 0.45, y: size.height * 0.2)
        controlT.size = CGSize(width: 400, height: 80)
        
        fireNormal.position = CGPoint(x: size.width * 0.75, y: size.height * 0.2)
        fireNormal.size = CGSize(width: 150, height: 150)
        
        firePressed.position = fireNormal.position
        firePressed.size = fireNormal.size
        
        controlCPercent.text = "0.0"
        controlCPercent.fontSize = 25
        controlCPercent.fontColor = SKColor.green
        controlCPercent.horizontalAlignmentMode = .left
        controlCPercent.position = CGPoint(x: 20, y: size.height * 0.84)
        
        controlCPos.text = "0.0"
        controlCPos.fontSize = 25
        controlCPos.fontColor = SKColor.blue
        controlCPos.horizontalAlignmentMode = .left
        controlCPos.position = CGPoint(x: 20, y: size.height * 0.8)
        
        turretPercent.text = "0.0"
        turretPercent.fontSize = 25
        turretPercent.fontColor = SKColor.red
        turretPercent.horizontalAlignmentMode = .left
        turretPercent.position = CGPoint(x: 20, y: size.height * 0.88)
        
        
        addChild(controlCPercent)
        addChild(controlCPos)
        addChild(turretPercent)
        
        addChild(controlT)
        addChild(controlC)
        addChild(fireNormal)
        firePressed.isHidden = true
        addChild(firePressed)
        
        //        DispatchQueue.global().async {
        //            var pending = false
        //            while(true){
        //                if(!pending){
        //                    pending = true
        //
        //                    let lval = Int(self.left!.percent * 127.0)
        //                    let rval = Int(self.right!.percent * 127.0)
        //
        //                    let url = "http://lbpi3:8080/mca/" + String(lval) + "/" + String(rval)
        //                    Alamofire.request(url).responseJSON { response in
        //                        pending = false
        //                        //NSLog(response.data.debugDescription)
        //                        //debugPrint("Ok1")
        //                    }
        //                }
        //                usleep(5000) //5ms => (1ms = 1000us)
        //            }
        //        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("b" + String(touches.count))
        
        for touch in touches{
            circlePad!.handleStart(touch: touch)
            turret!.handleStart(touch: touch)
            fire!.handleStart(touch: touch)
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        //NSLog("m" + String(touches.count))
        
        for touch in touches{
            circlePad!.handleMove(touch: touch)
            turret!.handleMove(touch: touch)
            fire!.handleMove(touch: touch)
        }
        updateUI()
        
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("e" + String(touches.count))
        
        for touch in touches{
            circlePad!.handleEnd(touch: touch)
            turret!.handleEnd(touch: touch)
            fire!.handleEnd(touch: touch)
        }
        updateUI()
    }
    
    func updateUI(){
        controlCPercent.text = String(format: "%.3f", circlePad!.percent)
        controlCPos.text = "blah"
        turretPercent.text = String(format: "%.3f", turret!.percent)
    }
}
