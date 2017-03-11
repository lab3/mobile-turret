import Foundation
import SpriteKit

class ControlState{
    var scene: GameScene
    var sprite: SKSpriteNode
    var touchDot: SKSpriteNode
    var active: Bool
    var start: CGPoint
    var myTouch: UITouch
    var vertical: Bool
    
    var percent:Double = 0.0
    
    init(scene: GameScene, sprite: SKSpriteNode, vertical: Bool){
        self.scene = scene
        self.sprite = sprite
        self.touchDot = SKSpriteNode(imageNamed: "touchdot")
        self.active = false
        self.start = CGPoint(x: 0, y: 0)
        self.touchDot.size = CGSize(width: 60, height: 60)
        self.myTouch = UITouch()
        self.vertical = vertical
    }
    
    func inactive() -> Bool {
        return !active
    }
    
    func calcPercent(touchLocation: CGPoint) {
        if(vertical){
            let max = Double(sprite.size.height) / 2
            var cur: Double
            if(start.y > touchLocation.y){
                cur = Double(start.y.subtracting(touchLocation.y))
            }else{
                cur = Double(touchLocation.y.subtracting(start.y))
            }
            
            if(cur > max){
                percent = 1.0
            }else{
                percent = Double(cur) / max
            }
        }
    }
    
    func handleStart(touch: UITouch) -> Bool {
        let touchLocation = touch.location(in: scene)
        
        if(inactive() && sprite.contains(touchLocation)){
            active = true
            if(vertical){
                touchDot.position.y = touchLocation.y
                touchDot.position.x = sprite.position.x
            }
            else{
                touchDot.position.x = touchLocation.x
                touchDot.position.y = sprite.position.y
            }
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
            calcPercent(touchLocation: touchLocation)
            if(percent != 1){
                if(vertical){
                    touchDot.position.y = touchLocation.y
                }
                else{
                    touchDot.position.x = touchLocation.x
                }
            }
            
            return true
        }
        
        return false
    }
    
    func handleEnd(touch: UITouch) -> Bool{
        if (myTouch == touch) {
            if(touchDot.parent != nil){
                touchDot.removeFromParent()
                active = false
                percent = 0
                return true
            }
        }
        
        return false
    }
}
