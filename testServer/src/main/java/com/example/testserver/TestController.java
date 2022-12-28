package com.example.testserver;

import com.example.testserver.dto.*;
import com.example.testserver.exception.CustomException;
import com.example.testserver.exception.ErrorCode;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.apache.coyote.Response;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class TestController {
    PostResponseDto postResponseDto = new PostResponseDto(1L, "test1", "범준", "내용", 0L, 0L, null, LocalDateTime.now(), false, new ArrayList<>(), "대충 그럴싸한 경로");
    Boolean like = null;
    @ApiOperation(value = "회원가입")
    @PostMapping("user/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody SignupRequestDto requestDto){
        if(requestDto.getUsername().equals("test1")){
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }
        if(!requestDto.getPassword().equals(requestDto.getCheckPassword())){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }


        ResponseMessage responseMessage = new ResponseMessage("회원가입 성공", 200, requestDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    @ApiOperation(value = "아이디 체크")
    @PostMapping("user/idcheck")
    public ResponseEntity<ResponseMessage> idCheck(@RequestBody IdCheckRequestDto requestDto){
        if(requestDto.getUsername().equals("test1")){
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        ResponseMessage responseMessage = new ResponseMessage("사용가능한 아이디입니다.", 200, requestDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    @ApiOperation(value = "로그인")
    @PostMapping("user/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        if(!requestDto.getUsername().equals("test1")){
            throw new CustomException(ErrorCode.USERNAME_NOT_FOUND);
        }
        if(!requestDto.getPassword().equals("qwer1234")){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }
        response.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VySW5mbyIsImlkIjoidGVzdDEiLCJleHAiOjE2NzIwMzIxMDMsImlhdCI6MTY3MjAyODUwM30.sVdXwe4l5UaOih3PgTUxXxmj76nvz_1xP31-GsAQ08U");
        UserResponseDto userResponseDto = new UserResponseDto("test1", "범준");
        ResponseMessage responseMessage = new ResponseMessage("로그인 성공", 200, userResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "유저 정보 조회")
    @GetMapping("/user")
    public ResponseEntity<ResponseMessage> getUser(){

        UserResponseDto userResponseDto = new UserResponseDto("test1", "범준");
        ResponseMessage responseMessage = new ResponseMessage("로그인 성공", 200, userResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "아지트 리스트")
    @GetMapping("/agit")
    public ResponseEntity<ResponseMessage> getAgit(){
        List<AgitResponseDto> agitResponseDtoList = new ArrayList<>();
        for(int i = 1; i <= 5; i++){
            agitResponseDtoList.add(new AgitResponseDto((long)i, "아지트이름"+ i));
        }
        ResponseMessage responseMessage = new ResponseMessage("조회 성공", 200, agitResponseDtoList);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "아지트 생성")
    @PostMapping("/agit")
    public ResponseEntity<ResponseMessage> createAgit(@RequestBody AgitRequestDto requestDto){
        ResponseMessage responseMessage = new ResponseMessage("생성 성공", 200, requestDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "아지트 초대")
    @PostMapping("/agit/{agitId}/join")
    public ResponseEntity<ResponseMessage> joinAgit(@RequestBody JoinRequestDto requestDto){
        String username = requestDto.getUsername();
        if(username.equals("test2")){
            throw new CustomException(ErrorCode.DUPLICATE_JOIN);
        }
        if(!username.equals("test1")) {
            throw new CustomException(ErrorCode.USERNAME_NOT_FOUND);
        }
        ResponseMessage responseMessage = new ResponseMessage("초대 성공", 200, requestDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 전체 조회")
    @GetMapping("/agit/{id}")
    public ResponseEntity<ResponseMessage> getPostList(@PathVariable long id){
        Long commentId = 1L;
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();;
            for(int l = 0; l < (i %6- 2) - i%3; l++){
                commentResponseDtoList.add(new CommentResponseDto(commentId++, "test1", "범준", commentId%3==0, "댓글"+commentId, LocalDateTime.now()));
            }
            postResponseDtoList.add(new PostResponseDto((long)i, "test1", "범준", "내용"+i, (long)i*3+2, (long)2+i, i%3==0?null:i%3==1, LocalDateTime.now(), i%2==0, commentResponseDtoList, "대충 그럴싸한 경로"));
        }
        MainResponseDto mainResponseDto = new MainResponseDto("그럴싸한 게시판", "그럴싸한 게시판입니다.", postResponseDtoList);
        ResponseMessage responseMessage = new ResponseMessage("조회 성공", 200, mainResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 생성")
    @PostMapping("agit/{agitId}/post")
    public ResponseEntity<ResponseMessage> createPost(@RequestBody PostRequestDto requestDto){

        this.postResponseDto = new PostResponseDto((long)1, "test1", "범준", requestDto.getContent(), (long)0, (long)0, null, LocalDateTime.now(), false, new ArrayList<>(), requestDto.getPicturePath());

        ResponseMessage responseMessage = new ResponseMessage("생성 성공", 200, postResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 수정")
    @PutMapping("agit/post/{postId}")
    public ResponseEntity<ResponseMessage> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto){
        if(postId > 10){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        if(this.postResponseDto.getContent().equals(requestDto.getContent())){
            throw new CustomException(ErrorCode.INVALID_UPDATE);
        }
        this.postResponseDto = new PostResponseDto(postId, "test1", "범준", requestDto.getContent(), (long)0, (long)0, null, LocalDateTime.now(), false, new ArrayList<>(), requestDto.getPicturePath());
        ResponseMessage responseMessage = new ResponseMessage("수정 성공", 200, postResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping("agit/post/{postId}")
    public ResponseEntity<ResponseMessage> deletePost(@PathVariable Long postId){
        if(postId > 10){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        ResponseMessage responseMessage = new ResponseMessage("삭제 성공", 200, null);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<ResponseMessage> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto){
        if(postId> 10){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        CommentResponseDto commentResponseDto = new CommentResponseDto((long)1, "test1", "범준", false, requestDto.getContent(), LocalDateTime.now());
        ResponseMessage responseMessage = new ResponseMessage("댓글 생성 성공", 200, commentResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/post/comment/{commentId}")
    public ResponseEntity<ResponseMessage> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto){
        if(commentId > 6){
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        CommentResponseDto commentResponseDto = new CommentResponseDto((long)1, "test1", "범준", false, requestDto.getContent(), LocalDateTime.now());
        ResponseMessage responseMessage = new ResponseMessage("댓글 수정 성공", 200, commentResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/post/comment/{commentId}")
    public ResponseEntity<ResponseMessage> deleteComment(@PathVariable Long commentId){
        if(commentId > 6){
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }

        ResponseMessage responseMessage = new ResponseMessage("댓글 삭제 성공", 200, null);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 좋아요")
    @PostMapping("/like/post/{postId}")
    public ResponseEntity<ResponseMessage> likePost(@PathVariable Long postId){
        if(postId > 10){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        if(like==null || !like){
            like = true;
        }else if(like){
            like = null;
        }

        LikeResponseDto likeResponseDto = LikeResponseDto.builder()
                .thisLike(like)
                .likeCount((long)10).build();
        ResponseMessage responseMessage = new ResponseMessage("게시물 좋아요 성공", 200, likeResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 싫어요")
    @PostMapping("/hate/post/{postId}")
    public ResponseEntity<ResponseMessage> hatePost(@PathVariable Long postId){
        if(postId > 10){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        if(like==null || like){
            like = false;
        }else if(!like){
            like = null;
        }

        LikeResponseDto likeResponseDto = LikeResponseDto.builder()
                .thisLike(like)
                .hateCount((long)10).build();
        ResponseMessage responseMessage = new ResponseMessage("게시물 싫어요 성공", 200, likeResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @ApiOperation(value = "아지트 멤버 조회")
    @GetMapping("/agit/{agitId}/member")
    public ResponseEntity<ResponseMessage> getMember(@PathVariable Long agitId){
        List<UserResponseDto> userResponseDto = new ArrayList<>();
        for(int i = 1; i <= 5; i++){
            userResponseDto.add(new UserResponseDto("test"+i, "버"+("어".repeat(i)) + "준"));
        }
        ResponseMessage responseMessage = new ResponseMessage("게시물 싫어요 성공", 200, userResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


    @ApiOperation(value = "토큰 조회")
    @GetMapping("/token")
    public ResponseEntity<ResponseMessage> getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        boolean check = token.equals("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VySW5mbyIsImlkIjoidGVzdDEiLCJleHAiOjE2NzIwMzIxMDMsImlhdCI6MTY3MjAyODUwM30.sVdXwe4l5UaOih3PgTUxXxmj76nvz_1xP31-GsAQ08U");
        TokenResponseDto tokenResponseDto = new TokenResponseDto(token, check ? "토큰이 일치합니다" : "토큰이 일치하지 않습니다.");
        ResponseMessage responseMessage = new ResponseMessage("현재 토큰 정보", 200, tokenResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


}
