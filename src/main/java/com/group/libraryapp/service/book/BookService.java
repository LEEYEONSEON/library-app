package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;


    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        //1. 책 정보를 가져온다.
        //2. 대출기록 정보를 확인해 대출중인지 확인 - 1번에서 존재하는 책의 대출여부를 확인해야 함
        //ㄴ 대출기록 정보를 확인하려면 UserLoanHistoryRepository클래스에 의존성이 필요해
        //ㄴ UserLoanHistoryRepository를 선언하고, 주입받는다.
        //ㄴ 조회하는 기능이 필요하므로, 아까와 비슷하게 함수를 UserLoanHistoryRepository 클래스에
        //ㄴ 이번에는 boolean을 반환하는 existsByBookNameAndIsReturn(String name, boolean isReturn)으로 만들어줌
        //3. 확인했는데, 대출중이라면 예외발생시킴

        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException:: new);

        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
            //이미 대출중이라 오류발생
            throw new IllegalArgumentException("이미 대출되어 있는 책입니다.");
        }
        //아니면 존재하는 책에 대해 대출가능
        //4. 유저 정보를 가져온다.
        //5. 가져온 유저정보와 책 정보를 기반으로 UserLoanHistory를 저장한다.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        //cascade 옵션이 사용됨
        user.loanBook(book.getName());
        //userLoanHistoryRepository.save(new UserLoanHistory(user.getId(), book.getName()));
    }

    @Transactional
    public void returnBook(BookReturnRequest request){
        //우리는 BookReturnRequest에 userName과 책이름을 받는데,
        //UserLoanHistoryRepository를 보면 userId와 책이름, isReturn이 존재함
        //따라서 userName을 통해 User 객체를 찾아서 userId를 알아내야 함
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.returnBook(request.getBookName());
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
//                .orElseThrow(IllegalArgumentException::new);
//        //찾은 대출기록을 반납 처리해주어야 함
//        history.doReturn();
        //아래 코드는 안사용해주어도 됨.
        //transaction이 시작됨에 따라 영속성컨텍스트가 존재해
        //변경이 자동 저장되기 때문
        //userLoanHistoryRepository.save(history);
    }
}
