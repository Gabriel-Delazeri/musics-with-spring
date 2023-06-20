package com.delazeri.music.reviews.repositories;

import com.delazeri.music.albums.entities.Album;
import com.delazeri.music.reviews.entities.Review;
import com.delazeri.music.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT r FROM Review as r WHERE r.album =:album AND r.user =:user")
    Review findByAlbumAndUser(@Param("album") Album album, @Param("user") User user);

    @Query(
            "SELECT new Review (r.id, r.album, r.user, r.comment, r.postedAt, r.rating, COUNT(l.id)) FROM Review r" +
                    " LEFT JOIN r.likes l" +
                    " WHERE r.album =:album " +
                    " GROUP BY r.id"
    )
    List<Review> findByAlbum(@Param("album") Album album);
}
