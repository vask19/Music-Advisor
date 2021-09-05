package advisor;

public interface Advisor {
     String newAlbums();
     String featured();
     String categories();
     String playlists(String nameOfCategory);
     String exit();
}
