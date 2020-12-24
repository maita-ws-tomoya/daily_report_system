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
        query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow = :login_employee AND f.followed = :employee"
    ),
    @NamedQuery(
        name = "getFollowRelation",
        query = "SELECT f FROM Follow AS f WHERE f.follow = :login_employee AND f.followed = :employee"
    ),
    @NamedQuery(
            name = "getFollowsReports",
            query = "SELECT f FROM Follow AS f WHERE f.follow = :login_employee"
    )
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "follow", length = 255, nullable = false)
    private Integer follow;

    @Column(name = "followed", length = 255, nullable = false)
    private Integer followed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }

}