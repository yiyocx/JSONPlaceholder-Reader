package yiyo.com.postreader.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelTouchCallback
import com.airbnb.epoxy.EpoxyViewHolder
import kotlin.math.abs

open class SwipeTouchCallback<T : EpoxyModel<*>>(
    epoxyController: EpoxyController,
    target: Class<T>,
    private val iconDrawable: Drawable
) : EpoxyModelTouchCallback<T>(epoxyController, target) {

    private val background = ColorDrawable()
    private val intrinsicWidth = iconDrawable.intrinsicWidth
    private val intrinsicHeight = iconDrawable.intrinsicHeight

    override fun getMovementFlagsForModel(model: T, adapterPosition: Int): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: EpoxyViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        background.setBounds(
            itemView.right + (dX.toInt()),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconMargin = (itemView.height - iconDrawable.intrinsicHeight) / 2
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        iconDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        iconDrawable.draw(c)

        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwipeProgressChanged(
        model: T,
        itemView: View?,
        swipeProgress: Float,
        canvas: Canvas?
    ) {
        val alpha = (abs(swipeProgress) * 255).toInt()
        iconDrawable.alpha = alpha
        background.color = Color.argb(alpha, 255, 0, 0)
    }
}