package advisor;



public class AuthMusicAdvisorDecorator extends MusicAdvisorDecorator{
    private boolean ifAuth = false;

    private final String MESSAGE_IF_NOT_AUTH = "Please, provide access for application.";
        public AuthMusicAdvisorDecorator(Advisor advisor) {
        super(advisor);
    }

    public boolean auth(){
        Controller.getAuthCode();
        Controller.getAccessToken();
        ifAuth = true;
        return true;
    }

    @Override
    public String newAlbums() {
        if (ifAuth){
            return super.newAlbums();

        }
        else return MESSAGE_IF_NOT_AUTH;



    }

    @Override
    public String featured() {
        if (ifAuth){
            return super.featured();
        }
        else return MESSAGE_IF_NOT_AUTH;
    }

    @Override
    public String categories() {
        if (ifAuth){
            return super.categories();
        }
        else return MESSAGE_IF_NOT_AUTH;
    }

    @Override
    public String playlists(String nameOfCategory) {
        if (ifAuth){
            return super.playlists(nameOfCategory);
        }
        else return MESSAGE_IF_NOT_AUTH;
    }

    @Override
    public String exit() {
        return super.exit();
    }


}
