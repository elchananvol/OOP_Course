public class RendererFactory {
    RendererFactory() {}

    Renderer buildRenderer(String str) {
        if (str.equals("console")) {
            return new ConsoleRenderer();
        }
        if (str.equals("none")) {
            return new VoidRenderer();
        }
        return null;
    }
}
