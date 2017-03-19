import SpriteKit
import Alamofire

class GameScene: SKScene {
    let controlT = SKSpriteNode(imageNamed: "dpad_lr")
    let controlC = SKSpriteNode(imageNamed: "dpad_full")
    
    let conrtolCR = SKLabelNode(fontNamed: "Arial")
    let conrtolCL = SKLabelNode(fontNamed: "Arial")
    let turretPercent = SKLabelNode(fontNamed: "Arial")
    
    let fireNormal = SKSpriteNode(imageNamed: "fire_button4")
    let firePressed = SKSpriteNode(imageNamed: "fire_button_pressed")
    
    var turret: SliderState? = nil
    var fire: ButtonState? = nil
    var circlePad: CirclePadState? = nil
    
    override func didMove(to view: SKView) {
        backgroundColor = SKColor.white
        
        controlC.position = CGPoint(x: size.width * 0.13, y: size.height * 0.2)
        controlC.size = CGSize(width: 200, height: 200)
        
        controlT.position = CGPoint(x: size.width * 0.45, y: size.height * 0.2)
        controlT.size = CGSize(width: 400, height: 80)
        
        fireNormal.position = CGPoint(x: size.width * 0.75, y: size.height * 0.2)
        fireNormal.size = CGSize(width: 150, height: 150)
        
        firePressed.position = fireNormal.position
        firePressed.size = fireNormal.size
        
        conrtolCR.text = "0.0"
        conrtolCR.fontSize = 25
        conrtolCR.fontColor = SKColor.green
        conrtolCR.horizontalAlignmentMode = .left
        conrtolCR.position = CGPoint(x: 20, y: size.height * 0.84)
        
        conrtolCL.text = "0.0"
        conrtolCL.fontSize = 25
        conrtolCL.fontColor = SKColor.blue
        conrtolCL.horizontalAlignmentMode = .left
        conrtolCL.position = CGPoint(x: 20, y: size.height * 0.8)
        
        turretPercent.text = "0.0"
        turretPercent.fontSize = 25
        turretPercent.fontColor = SKColor.red
        turretPercent.horizontalAlignmentMode = .left
        turretPercent.position = CGPoint(x: 20, y: size.height * 0.88)
        
        
        addChild(conrtolCR)
        addChild(conrtolCL)
        addChild(turretPercent)
        
        addChild(controlT)
        addChild(controlC)
        addChild(fireNormal)
        firePressed.isHidden = true
        addChild(firePressed)
        
        turret = SliderState(scene: self, sprite: controlT, vertical: false)
        circlePad = CirclePadState(scene: self, sprite: controlC)
        fire = ButtonState(scene: self, buttonSprite: fireNormal, buttonPressedSprite: firePressed)
        
        updateUI()
        
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
        conrtolCR.text = String(format: "R: %.0f%% (%.0f)", circlePad!.rPercent * 100, circlePad!.rPercent * 127)
        conrtolCL.text = String(format: "L: %.0f%% (%.0f)", circlePad!.lPercent * 100, circlePad!.lPercent * 127)
        turretPercent.text = String(format: "%.3f", turret!.percent)
        
    }
}
