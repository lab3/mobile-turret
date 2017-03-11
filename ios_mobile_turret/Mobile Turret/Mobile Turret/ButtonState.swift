import Foundation
import SpriteKit

class ButtonState{
    var scene: GameScene
    var buttonSprite: SKSpriteNode
    var buttonPressedSprite: SKSpriteNode
    var myTouch: UITouch

    var percent:Double = 0.0
    
    init(scene: GameScene, buttonSprite: SKSpriteNode, buttonPressedSprite: SKSpriteNode){
        self.scene = scene
        self.buttonSprite = buttonSprite
        self.buttonPressedSprite = buttonPressedSprite
        self.myTouch = UITouch()
    }

    func handleStart(touch: UITouch){
        let touchLocation = touch.location(in: scene)
        
        if(buttonSprite.contains(touchLocation)){
            buttonPressedSprite.isHidden = false
            buttonSprite.isHidden = true
            myTouch = touch
        }
    }
    
    func handleMove(touch: UITouch){
        if (myTouch == touch) {
            let touchLocation = touch.location(in: scene)
            if(buttonSprite.contains(touchLocation)){
                buttonPressedSprite.isHidden = false
                buttonSprite.isHidden = true
            }else{
                buttonSprite.isHidden = false
                buttonPressedSprite.isHidden = true
            }
        }
    }
    
    func handleEnd(touch: UITouch){
        if (myTouch == touch) {
            let touchLocation = touch.location(in: scene)
            if(buttonSprite.contains(touchLocation)){
                NSLog("fire")
            }
            buttonSprite.isHidden = false
            buttonPressedSprite.isHidden = true
        }
    }
}
