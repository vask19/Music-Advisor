package advisor;

public class MusicAdvisorDecorator implements Advisor{
    private Advisor advisor;

    public MusicAdvisorDecorator(Advisor advisor){
        this.advisor = advisor;
    }
    @Override
    public String newAlbums() {
        return advisor.newAlbums();
    }

    @Override
    public String featured() {
        return advisor.featured();
    }

    @Override
    public String categories() {
        return advisor.categories();
    }

    @Override
    public String playlists(String nameOfCategory) {
        return advisor.playlists(nameOfCategory);
    }

    @Override
    public String exit() {
        return advisor.exit();
    }
}
