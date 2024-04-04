package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    //select * from user_loan_history where book_name = ? and is_return = ?
    //의 sql문이 들어감
    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);
    //Optional<UserLoanHistory> findByUserIdAndBookName(long userId, String bookName);
}
