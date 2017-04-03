import SpriteKit
import Alamofire

class GameScene: SKScene {
    
    let viewer = UIImageView()

    let udp = UDPListener()
   
    let controlT = SKSpriteNode(imageNamed: "dpad_lr")
    let controlC = SKSpriteNode(imageNamed: "dpad_full")
    
    let labelLeftWheel = SKLabelNode(fontNamed: "Courier")
    let labelRightWheel = SKLabelNode(fontNamed: "Courier")
    let labelYawRatio = SKLabelNode(fontNamed: "Courier")
    let labelThrottlePercent = SKLabelNode(fontNamed: "Courier")
    let labelTurretPercent = SKLabelNode(fontNamed: "Courier")
    
    let fireNormal = SKSpriteNode(imageNamed: "fire_button4")
    let firePressed = SKSpriteNode(imageNamed: "fire_button_pressed")
    
    var turret: SliderState? = nil
    var fire: ButtonState? = nil
    var circlePad: CirclePadState? = nil
    var baseHost = "lbpi3:8080"
    
    let textField = UITextField()
    
    func setLabel(label:SKLabelNode, text: String, point: CGPoint, color:UIColor){
        label.text = text
        label.fontSize = 25
        label.fontColor = color
        label.horizontalAlignmentMode = .left
        label.position = point//CGPoint(x: 20, y: size.height * 0.88)
        addChild(label)
    }
    
    override func didMove(to view: SKView) {
        udp.start()
        backgroundColor = SKColor.white
        
        viewer.frame = CGRect(origin: CGPoint(x: size.width * 0.3, y: size.height * 0.25), size: CGSize(width: 320, height: 240))
        viewer.layer.borderColor = UIColor.black.cgColor
        viewer.layer.borderWidth = 2.0
        
        controlC.position = CGPoint(x: size.width * 0.13, y: size.height * 0.2)
        controlC.size = CGSize(width: 200, height: 200)
        
        controlT.position = CGPoint(x: size.width * 0.45, y: size.height * 0.2)
        controlT.size = CGSize(width: 400, height: 80)
        
        fireNormal.position = CGPoint(x: size.width * 0.75, y: size.height * 0.2)
        fireNormal.size = CGSize(width: 150, height: 150)
        
        firePressed.position = fireNormal.position
        firePressed.size = fireNormal.size
        
        textField.frame = CGRect(origin: CGPoint(x: size.width * 0.77, y: size.height * 0.05), size: CGSize(width: 200, height: 30))
        textField.text = baseHost
        textField.font = UIFont(name: "Arial", size: 20)
        textField.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
        
        setLabel(label: labelTurretPercent, text: "TR: 0.0", point: CGPoint(x: 20, y: size.height * 0.88), color: UIColor.green)
        setLabel(label: labelThrottlePercent, text: "TH: 0.0", point: CGPoint(x: 20, y: size.height * 0.84), color: UIColor.red)
        setLabel(label: labelYawRatio, text: "YW: 0.0", point: CGPoint(x: 20, y: size.height * 0.8), color: UIColor.red)
        setLabel(label: labelLeftWheel, text: "LW: 0.0", point: CGPoint(x: 20, y: size.height * 0.76), color: UIColor.red)
        setLabel(label: labelRightWheel, text:"RW: 0.0", point: CGPoint(x: 20, y: size.height * 0.72), color: UIColor.red)
        
        firePressed.isHidden = true
        addChild(firePressed)
        addChild(controlT)
        addChild(controlC)
        addChild(fireNormal)
        self.view?.addSubview(textField)
        self.view?.addSubview(viewer)
        
        turret = SliderState(scene: self, sprite: controlT, vertical: false)
        circlePad = CirclePadState(scene: self, sprite: controlC)
        fire = ButtonState(scene: self, buttonSprite: fireNormal, buttonPressedSprite: firePressed)
        
        updateUI()
        
                DispatchQueue.global().async {
                    var pending = false
                    while(true){
                        if(!pending){
                            pending = true
        
                            let lval = Int(self.circlePad!.lPercent * 127.0)
                            let rval = Int(self.circlePad!.rPercent * 127.0)
                            let url = "http://" + self.baseHost + "/mca/" + String(lval) + "/" + String(rval)
                            Alamofire.request(url).responseJSON { response in
                                pending = false
                                //NSLog(response.data.debugDescription)
                                //debugPrint("Ok1")
                            }
                        }
                        usleep(5000) //5ms => (1ms = 1000us)
                    }
                }
    }
    
    func textFieldDidChange(_ textField: UITextField) {
        self.baseHost = textField.text!
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("b" + String(touches.count))
        
        for touch in touches{
            circlePad!.handleStart(touch: touch)
            turret!.handleStart(touch: touch)
            fire!.handleStart(touch: touch)
        }
        updateUI()
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
        labelThrottlePercent.text = String(format: "TH: %.0f%% (%.0f)", circlePad!.throttlePercent * 100, circlePad!.throttlePercent * 127)
        labelYawRatio.text = String(format: "YW: %.0f%% (%.0f)", circlePad!.yawRatio * 100, circlePad!.yawRatio * 127)
        labelRightWheel.text = String(format: "RW: %.0f%% (%.0f)", circlePad!.rPercent * 100, circlePad!.rPercent * 127)
        labelLeftWheel.text = String(format: "LW: %.0f%% (%.0f)", circlePad!.lPercent * 100, circlePad!.lPercent * 127)
        labelTurretPercent.text = String(format: "TR: %.0f%% (%.0f)", turret!.percent * 100, turret!.percent * 127)
    }
}
