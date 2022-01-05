package com.amadeus.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "SESSION_DATA")
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USER_DATA", nullable = false)
  private Long user;

  @Column(name = "START_DATE", nullable = false)
  private Date startDate;

  @Column(name = "END_DATE")
  private Date endDate;

  @Transient
  private Boolean active;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser() {
    return user;
  }

  public void setUser(Long user) {
    this.user = user;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Boolean getActive() {
    Date now = new Date();
    if ((this.startDate != null && now.after(this.startDate))
        && (this.endDate == null || (this.endDate != null && now.before(this.endDate)))) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
