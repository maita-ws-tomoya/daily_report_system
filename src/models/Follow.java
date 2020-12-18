package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
        name = "getAllFollows",
        query = "SELECT f FROM Follow AS f ORDER BY f.id DESC"
    ),
    @NamedQuery(
        name = "getFollowsCount",
        query = "SELECT COUNT(f) FROM Follow AS f"
    ),
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "follow", length = 255, nullable = false)
    private String follow;

    @Column(name = "followed", length = 255, nullable = false)
    private String followed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.follow = followed;
    }

}