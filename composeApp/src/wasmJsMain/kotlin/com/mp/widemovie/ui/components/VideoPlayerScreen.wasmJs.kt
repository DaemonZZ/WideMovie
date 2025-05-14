package com.mp.widemovie.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLVideoElement


@Composable
actual fun VideoPlayerScreen(videoUrl: String, slug: String) {
    val containerId = "video-container"

    // Khởi tạo DOM chỉ một lần
    SideEffect {
        if (document.getElementById(containerId) == null) {
            val div = document.createElement("div")
            div.id = containerId
            document.body?.appendChild(div)
        }
    }

    // Chèn video khi có URL
    LaunchedEffect(videoUrl) {
        val container = document.getElementById(containerId) as? HTMLDivElement
        if (container != null) {
            // Xoá video cũ nếu có
            val oldVideo = document.getElementById("video") as? HTMLVideoElement
            oldVideo?.remove()

            val video = document.createElement("video") as HTMLVideoElement
            video.id = "video"
            video.controls = true
            video.autoplay = true
            video.muted = false
            video.setAttribute("playsinline", "true")
            val width = document.documentElement?.clientWidth ?: 640
            video.style.width = "${width}px"
            video.style.height = "${width * 9 / 16}px"
            video.style.backgroundColor = "black"
            video.style.maxWidth = "960px"
            video.style.display = "block"
            video.style.margin = "auto"
            video.style.border = "2px solid red"

            container.appendChild(video)
            video.scrollIntoView()
            // Gọi HLS.js để play .m3u8
            playM3U8WithHls(videoUrl)
        }
    }

}
fun playM3U8WithHls(videoUrl: String) {
    js("""
                if (Hls.isSupported()) {
                    const hls = new Hls();
                    console.log("[DEBUG] HLS.js được hỗ trợ, bắt đầu load");
                    hls.loadSource(videoUrl);
                    hls.attachMedia(video);
                    hls.on(Hls.Events.MANIFEST_PARSED, function () {
                     console.log("[DEBUG] MANIFEST_PARSED - bắt đầu play");
                        video.play();
                    });
                } else if (video.canPlayType('application/vnd.apple.mpegurl')) {
                 console.log("[DEBUG] Không dùng được HLS.js, fallback dùng native m3u8");
                    video.src = videoUrl;
                    console.log("[DEBUG] Đặt nguồn video");
                    video.addEventListener('loadedmetadata', function () {
                        video.play();
                    });
                } else {
                    alert("Trình duyệt không hỗ trợ HLS.");
                }
            """)
}