package models;

import java.sql.Timestamp;

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

@Table(name = "attendances")
@NamedQueries({
    @NamedQuery(
            name = "getLoginEmployeeAttendances",
            query = "SELECT a FROM Attendance AS a WHERE a.employee.id = :login_employee ORDER BY a.id DESC"
        ),
    @NamedQuery(
            name = "getLoginEmployeeAttendancesCount",
            query = "SELECT COUNT(a) FROM Attendance AS a WHERE a.employee.id = :login_employee"
        ),
    @NamedQuery(
            name = "getLoginEmployeeAttendancesOutTimeNullCount",
            query = "SELECT COUNT(a) FROM Attendance AS a WHERE a.employee.id = :login_employee AND a.out_time = Null"
        )
})
@Entity

public class Attendance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //以下の部分で、goodsテーブルにreportテーブルが紐づけている
    //紐づけの値は紐づけ先の主キーが使用される

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "in_time", nullable = false)
    private Timestamp in_time;

    @Column(name = "out_time", nullable = true)
    private Timestamp out_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public Timestamp getIn_time() {
        return in_time;
    }

    public void setIn_time(Timestamp in_time) {
        this.in_time = in_time;
    }

    public Timestamp getOut_time() {
        return out_time;
    }

    public void setOut_time(Timestamp out_time) {
        this.out_time = out_time;
    }

}
