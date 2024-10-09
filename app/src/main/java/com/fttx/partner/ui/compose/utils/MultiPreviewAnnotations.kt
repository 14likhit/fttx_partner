package com.fttx.partner.ui.compose.utils

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "English", showBackground = true)
annotation class LanguagePreviews

@Preview(name = "big", showBackground = true, device = "spec:width=1440px,height=3120px,dpi=480")
@Preview(name = "medium", showBackground = true, device = "spec:width=1080px,height=1920px,dpi=480")
@Preview(name = "small", showBackground = true, device = "spec:width=768px,height=1280px,dpi=320")
annotation class DevicePreviews
