import Foundation
import SpriteKit

class CirclePadState{
    var scene: GameScene
    var sprite: SKSpriteNode
    var touchDot: SKSpriteNode
    var active: Bool
    var start: CGPoint
    var myTouch: UITouch
    var rPercent:Double = 0.0
    var lPercent:Double = 0.0
    var multiplier: Double = 1.0
    
    init(scene: GameScene, sprite: SKSpriteNode){
        self.scene = scene
        self.sprite = sprite
        self.touchDot = SKSpriteNode(imageNamed: "touchdot")
        self.active = false
        self.start = CGPoint(x: sprite.position.x, y: sprite.position.y)
        
        NSLog("pos %f.0,%f.0",sprite.position.x, sprite.position.y)
        NSLog("pos %f.0,%f.0",self.start.x, self.start.y)
        NSLog("pos %f.0,%f.0",sprite.size.width, sprite.size.height)
        
        self.touchDot.size = CGSize(width: 60, height: 60)
        self.myTouch = UITouch()
    }
    
    func inactive() -> Bool {
        return !active
    }
    
    func calcPercent(touchLocation: CGPoint) {
        if(active){
            let max = Double(sprite.size.height) / 3
            var cur: Double
            if(start.y > touchLocation.y){
                multiplier = -1.0
                cur = Double(start.y.subtracting(touchLocation.y))
            }else{
                multiplier = 1.0
                cur = Double(touchLocation.y.subtracting(start.y))
            }
            
            if(cur > max){
                rPercent = 1.0
            }else{
                rPercent = Double(cur) / max
            }
            
            rPercent = rPercent * multiplier
        }else{
            rPercent = 0
        }
    }
    
    func handleStart(touch: UITouch) {
        let touchLocation = touch.location(in: scene)
        
        if(inactive() && sprite.contains(touchLocation)){
            active = true
            touchDot.position.y = touchLocation.y
            touchDot.position.x = touchLocation.x
            //start = touchLocation
            scene.addChild(touchDot)
            myTouch = touch
            calcPercent(touchLocation: touchLocation)
        }
    }
    
    func handleMove(touch: UITouch){
        if (myTouch == touch) {
            let touchLocation = touch.location(in: scene)
            calcPercent(touchLocation: touchLocation)
            touchDot.position.y = touchLocation.y
            touchDot.position.x = touchLocation.x

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
