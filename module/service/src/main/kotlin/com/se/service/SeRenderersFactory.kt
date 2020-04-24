package com.se.service

import android.os.Handler
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer
import com.google.android.exoplayer2.metadata.MetadataOutput
import com.google.android.exoplayer2.text.TextOutput
import com.google.android.exoplayer2.video.VideoRendererEventListener

/**
 *Author: gaojin
 *Time: 2020/4/24 9:41 PM
 */

class SeRenderersFactory() : RenderersFactory {
    override fun createRenderers(eventHandler: Handler
                                 , videoRendererEventListener: VideoRendererEventListener
                                 , audioRendererEventListener: AudioRendererEventListener
                                 , textRendererOutput: TextOutput
                                 , metadataRendererOutput: MetadataOutput
                                 , drmSessionManager: DrmSessionManager<FrameworkMediaCrypto>?): Array<Renderer> {
        return arrayOf(FfmpegAudioRenderer())
    }

}