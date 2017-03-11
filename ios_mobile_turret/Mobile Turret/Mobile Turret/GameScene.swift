import SpriteKit

class GameScene: SKScene {
    let controlL = SKSpriteNode(imageNamed: "dpad_slim")
    let controlR = SKSpriteNode(imageNamed: "dpad_slim")
    let controlT = SKSpriteNode(imageNamed: "dpad_lr")
    
    let leftPercent = SKLabelNode(fontNamed: "Arial")
    let rightPercent = SKLabelNode(fontNamed: "Arial")
    
    let fireNormal = SKSpriteNode(imageNamed: "fire_button4")
    let firePressed = SKSpriteNode(imageNamed: "fire_button_pressed")
    
    var left: ControlState? = nil
    var right: ControlState? = nil
    var turret: ControlState? = nil
    var fire: ButtonState? = nil
    
    override func didMove(to view: SKView) {
        backgroundColor = SKColor.white
        
        left = ControlState(scene: self, sprite: controlL, vertical: true)
        right = ControlState(scene: self, sprite: controlR, vertical: true)
        turret = ControlState(scene: self, sprite: controlT, vertical: false)
        fire = ButtonState(scene: self, buttonSprite: fireNormal, buttonPressedSprite: firePressed)
        
        controlL.position = CGPoint(x: size.width * 0.1, y: size.height * 0.2)
        controlL.size = CGSize(width: 200, height: 200)

        controlR.position = CGPoint(x: size.width - (size.width * 0.1), y: size.height * 0.2)
        controlR.size = CGSize(width: 200, height: 200)

        controlT.position = CGPoint(x: size.width * 0.40, y: size.height * 0.2)
        controlT.size = CGSize(width: 400, height: 80)

        fireNormal.position = CGPoint(x: size.width - (size.width * 0.25), y: size.height * 0.2)
        fireNormal.size = CGSize(width: 150, height: 150)
        
        firePressed.position = fireNormal.position
        firePressed.size = fireNormal.size
        
        leftPercent.text = "0.0"
        leftPercent.fontSize = 25
        leftPercent.fontColor = SKColor.green
        leftPercent.horizontalAlignmentMode = .left
        leftPercent.position = CGPoint(x: 20, y: size.height * 0.84)

        rightPercent.text = "0.0"
        rightPercent.fontSize = 25
        rightPercent.fontColor = SKColor.green
        rightPercent.horizontalAlignmentMode = .left
        rightPercent.position = CGPoint(x: 20, y: size.height * 0.8)
        
        addChild(leftPercent)
        addChild(rightPercent)
        
        addChild(controlL)
        addChild(controlR)
        addChild(controlT)
        addChild(fireNormal)
        firePressed.isHidden = true
        addChild(firePressed)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("b" + String(touches.count))
        
        for touch in touches{
            left!.handleStart(touch: touch) ||
                right!.handleStart(touch: touch) ||
                turret!.handleStart(touch: touch)
            
            fire!.handleStart(touch: touch)
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("m" + String(touches.count))
        
        for touch in touches{
            left!.handleMove(touch: touch) ||
                right!.handleMove(touch: touch) ||
                turret!.handleMove(touch: touch)
            
            fire!.handleMove(touch: touch)
        }

        leftPercent.text = String(format: "%.2f", left!.percent)
        rightPercent.text = String(format: "%.2f", right!.percent)

    }
    
   override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("e" + String(touches.count))
        
        for touch in touches{
            left!.handleEnd(touch: touch) ||
                right!.handleEnd(touch:touch) ||
                turret!.handleEnd(touch: touch)
            
             fire!.handleEnd(touch: touch)
        }
    
    leftPercent.text = String(format: "%.2f", left!.percent)
    rightPercent.text = String(format: "%.2f", right!.percent)
    }
    
}
