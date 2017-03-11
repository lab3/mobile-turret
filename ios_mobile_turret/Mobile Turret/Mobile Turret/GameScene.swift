import SpriteKit

class GameScene: SKScene {
    let controlL = SKSpriteNode(imageNamed: "dpad_slim")
    let controlR = SKSpriteNode(imageNamed: "dpad_slim")
    let controlT = SKSpriteNode(imageNamed: "dpad_lr")
    let fire = SKSpriteNode(imageNamed: "fire_button4")
    
    var left: ControlState? = nil
    var right: ControlState? = nil
    var turret: ControlState? = nil

    
    override func didMove(to view: SKView) {
        backgroundColor = SKColor.white
        
        left = ControlState(scene: self, sprite: controlL)
        right = ControlState(scene: self, sprite: controlR)
        turret = ControlState(scene: self, sprite: controlT)
        
        controlL.position = CGPoint(x: size.width * 0.1, y: size.height * 0.2)
        controlL.size = CGSize(width: 200, height: 200)

        controlR.position = CGPoint(x: size.width - (size.width * 0.1), y: size.height * 0.2)
        controlR.size = CGSize(width: 200, height: 200)

        controlT.position = CGPoint(x: size.width * 0.40, y: size.height * 0.2)
        controlT.size = CGSize(width: 400, height: 80)

        fire.position = CGPoint(x: size.width - (size.width * 0.25), y: size.height * 0.2)
        fire.size = CGSize(width: 150, height: 150)
        
        addChild(controlL)
        addChild(controlR)
        addChild(controlT)
        addChild(fire)
        
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("b" + String(touches.count))
        
        for touch in touches{
            left!.handleStart(touch: touch) ||
                right!.handleStart(touch: touch) ||
                turret!.handleStart(touch: touch)
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("m" + String(touches.count))
        
        for touch in touches{
            left!.handleMove(touch: touch) ||
                right!.handleMove(touch: touch) ||
                turret!.handleMove(touch: touch)
        }
    }
    
   override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        NSLog("e" + String(touches.count))
        
        for touch in touches{
            left!.handleEnd(touch: touch) ||
                right!.handleEnd(touch:touch) ||
                turret!.handleEnd(touch: touch)
        }
    }
    
}
