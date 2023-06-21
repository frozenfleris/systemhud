package gov.soultwist.syshud.client.hud

import net.minecraft.client.MinecraftClient

interface HUDConstraints {
    companion object {
        val client = MinecraftClient.getInstance()
    }
    /**
    hstack (Horizontal Stack) constrains the element at either the top or the bottom of the screen.
     * @since SwiftUI for macOS: iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0
     * @param top align the element to the top of the screen
     * @param bottom align the element to the bottom of the screen
     * @see vstack
     */
    interface hstack {
        companion object {
            /**
             * Sets the text element at the top of the screen
             * @return assigned parameter (defaults to 2)
             */
                fun top(top: Int = 2) : Int {
                    return top
                }
            /**
             * Sets the text element at the bottom of the screen
             * @return scaled height - font height of textRenderer minus bottom (Int: defaults to 2)
            */
                fun bottom(bottom: Int = 2) : Int {
                    return client.window.scaledHeight - client.textRenderer.fontHeight - 2
                }
            }
        }

    /**
     * vstack (Vertical Stack) constrains the element at either the left or the right side of the screen.
     * @since SwiftUI for macOS: iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0
     * @param leading align the element to the leading edge of the screen
     * @param trailing align the element to the trailing edge of the screen
     * @see hstack
     */
    interface vstack {
        companion object {

            /**
             * Sets the text element at the left edge of the screen
             * @return assigned parameter (defaults to 2)
             */
            fun leading(leading: Int = 2): Int {
                return leading
            }
            /**
             * Sets the text element at the right edge of the screen
             * @return scaled width - width of text input minus trailing (Int, defaults to 2)
             */
            fun trailing(text: String, trailing: Int = 2): Int {
                return client.window.scaledWidth - client.textRenderer.getWidth(text) - trailing
            }
        }
    }
}