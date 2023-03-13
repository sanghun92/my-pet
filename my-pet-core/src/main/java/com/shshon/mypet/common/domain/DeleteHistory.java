package com.shshon.mypet.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Embeddable
@Getter
public class DeleteHistory {

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected DeleteHistory() {
        this.isDeleted = null;
        this.deletedAt = null;
    }

    private DeleteHistory(Boolean isDeleted, LocalDateTime deletedAt) {
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public static DeleteHistory nonDeleted() {
        return new DeleteHistory(false, null);
    }

    public void writeDeleteHistory() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
