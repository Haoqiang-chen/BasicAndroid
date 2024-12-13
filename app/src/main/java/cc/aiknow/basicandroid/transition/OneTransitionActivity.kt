package cc.aiknow.basicandroid.transition

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cc.aiknow.basicandroid.R
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class OneTransitionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Set up shared element transition
        val target: View = findViewById(android.R.id.content)
        target.transitionName = "sharedElement"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform(this, false).apply {
            addTarget(target)
            scrimColor = Color.TRANSPARENT
            duration = 500
        }
        window.sharedElementExitTransition = MaterialContainerTransform().apply {
            addTarget(target)
            scrimColor = Color.TRANSPARENT

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_transition)
    }
}