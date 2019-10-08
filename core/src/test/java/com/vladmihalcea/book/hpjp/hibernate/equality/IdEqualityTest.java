package com.vladmihalcea.book.hpjp.hibernate.equality;

import com.vladmihalcea.book.hpjp.hibernate.identifier.Identifiable;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;

/**
 * @author Vlad Mihalcea
 */
public class IdEqualityTest
        extends AbstractEqualityCheckTest<IdEqualityTest.Post> {

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
                Post.class
        };
    }

    @Test
    public void testEquality() {
        Post post = new Post();
        post.setTitle("High-PerformanceJava Persistence");

        assertEqualityConsistency(Post.class, post);
    }

    @Test
    public void testHashSetDuplicates(){
        Post postHello = new Post();
        postHello.setTitle("Hello"); // id=null, post="Hello"

        Post postHowAreYou = new Post();
        postHowAreYou.setTitle("How Are You");

        Post postBye = new Post();
        postBye.setTitle("Bye Bye");

        Post postHelloAgain = new Post();
        postHelloAgain.setTitle("Hello");  // Duplicate: id=null, post="Hello"

        HashSet<Object> posts = new HashSet<>();

        posts.add(postHello);
        posts.add(postHowAreYou);
        posts.add(postBye);
        posts.add(postHelloAgain);

        Assert.assertEquals("Set contains duplicates", 4, posts.size());


    }

    @Entity(name = "Post")
    @Table(name = "post")
    public static class Post implements Identifiable<Long> {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        public Post() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof Post))
                return false;

            Post other = (Post) o;

            return id != null && id.equals(other.getId());
        }

        @Override
        public int hashCode() {
            return 31;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
