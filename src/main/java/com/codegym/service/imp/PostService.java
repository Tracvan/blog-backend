package com.codegym.service.imp;

import com.codegym.model.Comment;
import com.codegym.model.Mode;
import com.codegym.model.Post;
import com.codegym.model.User;
import com.codegym.model.dto.CommentDTO;
import com.codegym.model.dto.PostDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.repository.IPostRepository;
import com.codegym.service.IPostService;
import com.codegym.service.IUserService;
import com.codegym.service.InfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    IPostRepository postRepository;
    @Autowired
    IUserService userService;
    @Override
    public List<PostDTO> getAllPostInfo() {
        List<Post> postList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();

        for(var i= 0; i < postList.size(); i++){
           Long id = postList.get(i).getId();
           Long userId = postList.get(i).getUser().getId();
            UserDetailDTO userDetailDTO = userService.getUserDetailById(userId);
            //post information
            String title = postList.get(i).getTitle();
            LocalDate time = postList.get(i).getTime();
            String content = postList.get(i).getContent();
            String image = postList.get(i).getImage();
            String description = postList.get(i).getDescription();
            Mode mode = postList.get(i).getMode();
            List<Comment> comments = postList.get(i).getComments();
            List<CommentDTO> commentDTOList = new ArrayList<>();

            for(var z = 0; z < comments.size(); z++) {
                Long commentId = comments.get(z).getId();
                String commentContent = comments.get(z).getContent();
                LocalDate commentTime = comments.get(z).getTime();
                User commentUser = comments.get(z).getUser();
                UserDetailDTO commentUserDetailDTO = userService.getUserDetailById(commentUser.getId());
                String commentAvatar = commentUserDetailDTO.getAvatar();
                String commentUsername= commentUserDetailDTO.getUsername();
                CommentDTO commentDTO = new CommentDTO(commentId,commentContent,commentTime,commentAvatar,commentUsername);
                commentDTOList.add(commentDTO);
            }
            //user information
            String userAvatar = userDetailDTO.getAvatar();
            String username = userDetailDTO.getUsername();
            PostDTO postDTO = new PostDTO(id,title,time, content,image,description,mode,userAvatar,username, commentDTOList);
           postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    @Override
    public PostDTO getPostDTOById(Long id) {
        Post post = postRepository.findById(id).get();
        Long userId = post.getUser().getId();
        Long postId = post.getId();
        UserDetailDTO userDetailDTO = userService.getUserDetailById(userId);
        String title = post.getTitle();
        LocalDate time = post.getTime();
        String content =post.getContent();
        String image = post.getImage();
        String description = post.getDescription();
        Mode mode = post.getMode();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        List<Comment> comments = post.getComments();
        for(var i = 0; i <comments.size(); i++) {
            Long commentId = comments.get(i).getId();
            String commentContent = comments.get(i).getContent();
           LocalDate commentTime = comments.get(i).getTime();
           User commentUser = comments.get(i).getUser();
           UserDetailDTO commentUserDetailDTO = userService.getUserDetailById(commentUser.getId());
           String commentAvatar = commentUserDetailDTO.getAvatar();
           String commentUsername= commentUserDetailDTO.getUsername();
            CommentDTO commentDTO = new CommentDTO(commentId,commentContent,commentTime,commentAvatar,commentUsername);
            commentDTOList.add(commentDTO);
        }
        String userAvatar = userDetailDTO.getAvatar();
        String username = userDetailDTO.getUsername();
        String currentUsername = userService.getCurrentUser().getUsername();
        boolean isOwner = false;
        if(currentUsername.equals(username)){
            isOwner = true;
        }
        PostDTO postDTO = new PostDTO(postId,title,time, content,image,description,mode,userAvatar,username,commentDTOList,isOwner );
        return postDTO;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDTO> getAllPublicPostInfo(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<PostDTO> postDTOList = postRepository.getAllPublicPost(pageable);
        for(var i =0; i < postDTOList.size(); i++){
            String userDetailAvatar = userService.getUserDetailDTOByUsername(postDTOList.get(i).getUsername()).getAvatar();
            postDTOList.get(i).setUserAvatar(userDetailAvatar);
            List<CommentDTO> commentDTOList = new ArrayList<>();
            Post post = postRepository.findById(postDTOList.get(i).getId()).get();
            List<Comment> comments = post.getComments();
            for(var z = 0; z <comments.size(); z++) {
                Long commentId = comments.get(z).getId();
                String commentContent = comments.get(z).getContent();
                LocalDate commentTime = comments.get(z).getTime();
                User commentUser = comments.get(z).getUser();
                UserDetailDTO commentUserDetailDTO = userService.getUserDetailById(commentUser.getId());
                String commentAvatar = commentUserDetailDTO.getAvatar();
                String commentUsername= commentUserDetailDTO.getUsername();
                CommentDTO commentDTO = new CommentDTO(commentId,commentContent,commentTime,commentAvatar,commentUsername);
                commentDTOList.add(commentDTO);
            }
            postDTOList.get(i).setCommentsDTO(commentDTOList);
        }
        return  postDTOList;
    }

    @Override
    public List<PostDTO> getAllMyPost(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        UserDetailDTO userDetailDTO = userService.getCurrentUser();
        Long userId = userDetailDTO.getId();
        List<PostDTO> myPostList = postRepository.getAllByUser(pageable,userId);
        for(var i =0; i < myPostList.size(); i++){
            String userDetailAvatar = userService.getUserDetailDTOByUsername(myPostList.get(i).getUsername()).getAvatar();
            myPostList.get(i).setUserAvatar(userDetailAvatar);
            List<CommentDTO> commentDTOList = new ArrayList<>();
            Post post = postRepository.findById(myPostList.get(i).getId()).get();
            List<Comment> comments = post.getComments();
            for(var z = 0; z <comments.size(); z++) {
                Long commentId = comments.get(z).getId();
                String commentContent = comments.get(z).getContent();
                LocalDate commentTime = comments.get(z).getTime();
                User commentUser = comments.get(z).getUser();
                UserDetailDTO commentUserDetailDTO = userService.getUserDetailById(commentUser.getId());
                String commentAvatar = commentUserDetailDTO.getAvatar();
                String commentUsername= commentUserDetailDTO.getUsername();
                CommentDTO commentDTO = new CommentDTO(commentId,commentContent,commentTime,commentAvatar,commentUsername);
                commentDTOList.add(commentDTO);
            }
            myPostList.get(i).setCommentsDTO(commentDTOList);
        }
        return  myPostList;
    }

    @Override
    public List<PostDTO> findPostByTitle(String input, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<PostDTO> myPostList = postRepository.findByTitle(pageable,input);
        for(var i =0; i < myPostList.size(); i++){
            String userDetailAvatar = userService.getUserDetailDTOByUsername(myPostList.get(i).getUsername()).getAvatar();
            myPostList.get(i).setUserAvatar(userDetailAvatar);
            List<CommentDTO> commentDTOList = new ArrayList<>();
            Post post = postRepository.findById(myPostList.get(i).getId()).get();
            List<Comment> comments = post.getComments();
            for(var z = 0; z <comments.size(); z++) {
                Long commentId = comments.get(z).getId();
                String commentContent = comments.get(z).getContent();
                LocalDate commentTime = comments.get(z).getTime();
                User commentUser = comments.get(z).getUser();
                UserDetailDTO commentUserDetailDTO = userService.getUserDetailById(commentUser.getId());
                String commentAvatar = commentUserDetailDTO.getAvatar();
                String commentUsername= commentUserDetailDTO.getUsername();
                CommentDTO commentDTO = new CommentDTO(commentId,commentContent,commentTime,commentAvatar,commentUsername);
                commentDTOList.add(commentDTO);
            }
            myPostList.get(i).setCommentsDTO(commentDTOList);
        }
        return  myPostList;
    }


    @Autowired
    InfoUserService infoUserService;
    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public UserDetailDTO getUserDetailByUser(User user) {
        return infoUserService.findInforUserById(user.getId());
    }
}
