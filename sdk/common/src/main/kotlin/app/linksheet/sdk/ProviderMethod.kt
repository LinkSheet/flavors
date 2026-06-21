package app.linksheet.sdk

import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

public object MessageHandlers {
    //    val PreRule = PreRuleMessageHandler
//    val PostRule = PostRuleMessageHandler
//    private val handlers: List<ContentProviderMessageHandler<out Message>> = listOf(PreRule, PostRule)
//
//    fun getHandler(type: String): ContentProviderMessageHandler<out Message>? {
//        return handlers.firstOrNull { it.type == type }
//    }
    public const val PreRule: String = "pre_rule"
    public const val PostRule: String = "post_rule"
}

public data class PluginMethod<I : Message>(val name: String, val input: KClass<I>) {
    public companion object {
        public val PreRule: PluginMethod<PreRuleMessage> = PluginMethod("pre_rule", PreRuleMessage::class)
        public val PostRule: PluginMethod<PostRuleMessage> = PluginMethod("post_rule", PostRuleMessage::class)
    }
}

@Parcelize
public sealed interface Message : Parcelable

public sealed interface Exchange<I : Message, O : Message>

public class PreRuleExchange : Exchange<PreRuleExchange.Input, PreRuleExchange.Output> {
    public class Input(public val url: String) : Message
    public class Output : Message
}

public sealed interface ContentProviderMessageHandler<M : Message> {
    public val type: String
    public fun toBundle(data: M): Bundle
    public fun fromBundle(bundle: Bundle): M?
}

public data class PreRuleMessage(val url: String) : Message
public data class PostRuleMessage(val originalUrl: String, val resultUrl: String) : Message

public object PreRuleMessageHandler : ContentProviderMessageHandler<PreRuleMessage> {
    override val type: String = "pre_rule"
    override fun toBundle(data: PreRuleMessage): Bundle {
        return Bundle().apply {
            putString("url", data.url)
        }
    }

    override fun fromBundle(bundle: Bundle): PreRuleMessage? {
        val url = bundle.getString("url") ?: return null
        return PreRuleMessage(url)
    }
}

public object PostRuleMessageHandler : ContentProviderMessageHandler<PostRuleMessage> {
    override val type: String = "post_rule"
    override fun toBundle(data: PostRuleMessage): Bundle {
        return Bundle().apply {
            putString("original_url", data.originalUrl)
            putString("result_url", data.resultUrl)
        }
    }

    override fun fromBundle(bundle: Bundle): PostRuleMessage? {
        val originalUrl = bundle.getString("original_url") ?: return null
        val resultUrl = bundle.getString("result_url") ?: return null
        return PostRuleMessage(originalUrl, resultUrl)
    }
}
