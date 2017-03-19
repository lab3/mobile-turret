import Foundation
import SpriteKit

class CirclePad{
    var scene: GameScene
    var sprite: SKSpriteNode
    var touchDot: SKSpriteNode
    var active: Bool
    var start: CGPoint
    var myTouch: UITouch
    var percent:Double = 0.0
    var posX:Double = 0.0
    var posY:Double = 0.0
    var multiplier: Double = 1.0
    
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
    
    func calcPercent(touchLocation: CGPoint) {
        if(active){
            let max = Double(sprite.size.height) / 2
            var cur: Double
            if(start.y > touchLocation.y){
                multiplier = -1.0
                cur = Double(start.y.subtracting(touchLocation.y))
            }else{
                multiplier = 1.0
                cur = Double(touchLocation.y.subtracting(start.y))
            }
            
            if(cur > max){
                percent = 1.0
            }else{
                percent = Double(cur) / max
            }
            
            percent = percent * multiplier
        }else{
            percent = 0
        }
    }
    
    func handleStart(touch: UITouch) {
        let touchLocation = touch.location(in: scene)
        
        if(inactive() && sprite.contains(touchLocation)){
            active = true
            touchDot.position.y = touchLocation.y
            touchDot.position.x = touchLocation.x
            start = touchLocation
            scene.addChild(touchDot)
            myTouch = touch
        }
    }
    
    func handleMove(touch: UITouch){
        if (myTouch == touch) {
            let touchLocation = touch.location(in: scene)
            calcPercent(touchLocation: touchLocation)
            if(percent != 1){
                touchDot.position.y = touchLocation.y
                touchDot.position.x = touchLocation.x
            }
        }
    }
    
    func handleEnd(touch: UITouch){
        if (myTouch == touch) {
            if(touchDot.parent != nil){
                touchDot.removeFromParent()
                active = false
                calcPercent(touchLocation: touch.location(in: scene))
            }
        }
    }
}
