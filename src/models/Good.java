package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Table(name = "goods")
@NamedQueries({
    @NamedQuery(
        name = "getReportGoodsCount",
        query = "SELECT COUNT(g) FROM Good AS g WHERE g.report.id = :report_id"
        ),
    @NamedQuery(
        name = "getGoodsCount",
        query = "SELECT COUNT(g) FROM Good AS g WHERE g.do_good = :login_employee AND g.report.id = :report_id"
        ),
    @NamedQuery(
            name = "getAllGoodsCount",
            query = "SELECT COUNT(g) FROM Good AS g WHERE g.report.id = :report_id"
            ),
    @NamedQuery(
        name = "getGoods",
        query = "SELECT g FROM Good AS g WHERE g.do_good = :login_employee AND g.report.id = :report_id"
        )
})
@Entity
public class Good {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //以下の部分で、goodsテーブルにreportテーブルが紐づけている
    //紐づけの値は紐づけ先の主キーが使用される

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "do_good", nullable = false)
    private Integer do_good;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Integer getDoGood() {
        return do_good;
    }

    public void setDoGood(Integer do_good) {
        this.do_good = do_good;
    }
}