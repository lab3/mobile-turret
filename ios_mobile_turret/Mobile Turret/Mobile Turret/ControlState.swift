import Foundation
import SpriteKit

class ControlState{
    var scene: GameScene
    var sprite: SKSpriteNode
    var touchDot: SKSpriteNode
    var active: Bool
    var start: CGPoint
    var myTouch: UITouch
    
    init(scene: GameScene, sprite: SKSpriteNode){
        self.scene = scene
        self.sprite = sprite
        self.touchDot = SKSpriteNode(imageNamed: "touchdot")
        self.active = false
        self.start = CGPoint(x: 0, y: 0)
        self.touchDot.size = CGSize(width: 60, height: 60)
        self.myTouch = UITouch()
    }
    
    func inactive() -> Bool {
        return !active
    }
    
    func handleStart(touch: UITouch) -> Bool {
        let touchLocation = touch.location(in: scene)
        
        if(inactive() && sprite.contains(touchLocation)){
            active = true
            touchDot.position = touchLocation
            start = touchLocation
            scene.addChild(touchDot)
            myTouch = touch
            return true
        }
        
        return false
    }
    
    func handleMove(touch: UITouch) -> Bool{
        if (myTouch == touch) {
            let touchLocation = touch.location(in: scene)
            touchDot.position = touchLocation
            return true
        }
        
        return false
    }
    
    func handleEnd(touch: UITouch) -> Bool{
        if (myTouch == touch) {
            if(touchDot.parent != nil){
                touchDot.removeFromParent()
                active = false
                return true
            }
        }
        
        return false
    }
}
