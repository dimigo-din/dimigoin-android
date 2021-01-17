package `in`.dimigo.dimigoin.ui.card

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.ActivityCardBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardBinding.inflate(layoutInflater).apply {
            val userModel = UserDataStore.userData
            user = userModel
            department = resources.getStringArray(R.array.departments)[userModel.`class` - 1]
        }
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.profileImage.clipToOutline = true
        if (UserDataStore.userData.photo.isNotEmpty()) {
            Glide.with(this)
                .load(DimigoinService.getProfileUrl(UserDataStore.userData.photo.last()))
                .into(binding.profileImage)
        }
    }
}
