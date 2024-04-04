package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @JoinColumn(nullable = false)
    @ManyToOne  //대출기록은 여러개이고 각각에 해당하는 user는 하나
                //나는 1나 상대방은 여러개
    private User user;

    private String bookName;
    //db에 0이들어가면 false, db에 1이 들어가면 true로 매핑시키기
    private boolean isReturn;


    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean isReturn() {
        return isReturn;
    }


    protected UserLoanHistory(){
    }

    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public void doReturn() {
        isReturn = true;
    }
}
