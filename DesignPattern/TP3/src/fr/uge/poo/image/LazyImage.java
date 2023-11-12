package fr.uge.poo.image;

public class LazyImage implements Image {
    private final String url;
    private Image image = null;
    public LazyImage(String url) {
        this.url = url;
    }

    public Image initialize(){
        image = Image.download(url);
        return image;
    }

    @Override
    public String name() {
        initialize();
        return image.name();
    }

    @Override
    public int size() {
        initialize();
        return image.size();
    }

    @Override
    public double hue() {
        initialize();
        return image.hue();
    }
}
