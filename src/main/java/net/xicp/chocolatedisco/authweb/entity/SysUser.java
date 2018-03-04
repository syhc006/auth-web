package net.xicp.chocolatedisco.authweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.xicp.chocolatedisco.authweb.validator.group.AddValidationGroup;
import net.xicp.chocolatedisco.authweb.validator.group.EditValidationGroup;
import net.xicp.chocolatedisco.authweb.validator.group.QueryValidationGroup;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
@Entity
public class SysUser {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "账号不能为空", groups = {AddValidationGroup.class})
    @Pattern(regexp = "\\w{1,32}", message = "账号不能包含非法字符", groups = {AddValidationGroup.class, EditValidationGroup.class, QueryValidationGroup.class})
    @Length(message = "账号长度不得超过32个字符", min = 1, max = 32, groups = {AddValidationGroup.class, EditValidationGroup.class, QueryValidationGroup.class})
    @Column(nullable = false, unique = true)
    private String account;
    @Column(nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private SysRole sysRole;
    @Column(nullable = true)
    private Integer errorNumber;
    @Column(nullable = true)
    private Long errorTime;

    @JsonIgnore
    public boolean isAccountNonLocked() {
        if (errorNumber == null || errorTime == null) {
            return true;
        } else if (errorNumber >= 5 && System.currentTimeMillis() - errorTime <= 5 * 60 * 1000) {
            return false;
        } else {
            return true;
        }
    }

    @JsonIgnore
    public Collection<String> getAuthorities() {
        List<String> authorities = new ArrayList<>();
        Optional.ofNullable(this.getSysRole())
                .map(role -> sysRole.getSysResources())
                .ifPresent(resource -> {
                    resource.stream().forEach(
                            sysResource -> authorities.add(sysResource.getCode())
                    );
                });
        return authorities;
    }
}
