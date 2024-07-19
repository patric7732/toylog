package org.example.toylog.domain.follow.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.toylog.domain.follow.entity.Follow;
import org.example.toylog.domain.follow.repository.FollowRepository;
import org.example.toylog.domain.user.entity.User;
import org.example.toylog.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void toggleFollow(User follower, User following) {
        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (follower == following){
            throw new RuntimeException("follower is already following");
        }

        if (existingFollow.isPresent()) {
            throw new RuntimeException("Following is already following");
        } else {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
        }
    }

    public void unfollowUser(User follower, User following) {
        Optional<Follow> follow = followRepository.findByFollowerAndFollowing(follower, following);
        if (follow.isPresent()) {
            followRepository.delete(follow.orElseThrow(() -> new RuntimeException("Following is already not following"))
            );
        }
    }

    public List<Follow> getFollowers(User user) {
        return followRepository.findByFollowing(user);
    }

    public List<Follow> getFollowing(User user) {
        return followRepository.findByFollower(user);
    }

    public boolean isFollowing(User user, User currentUser) {
        return followRepository.existsByFollowerAndFollowing(user, currentUser);
    }
}
