package org.example.toylog.domain.follow.repository;

import java.util.List;
import java.util.Optional;
import org.example.toylog.domain.follow.entity.Follow;
import org.example.toylog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    boolean existsByFollowerAndFollowing(User follower, User following);

    void deleteByFollower(User follower);
}
