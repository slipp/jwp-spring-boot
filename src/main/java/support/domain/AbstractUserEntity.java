package support.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.slipp.UnAuthorizedException;
import net.slipp.domain.User;

@MappedSuperclass
public class AbstractUserEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn
    private User writer;
    
    protected AbstractUserEntity() {
    }
    
    public AbstractUserEntity(long id, User writer) {
        super(id);
        this.writer = writer;
    }
    
    public User getWriter() {
        return writer;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }
    
    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }
    
    public void verifyAuthorizedOwner(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + " AbstractUserEntity [writer=" + writer + "]";
    }
}