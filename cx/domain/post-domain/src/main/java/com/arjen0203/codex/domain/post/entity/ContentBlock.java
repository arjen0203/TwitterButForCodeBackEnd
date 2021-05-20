package com.arjen0203.codex.domain.post.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ContentBlock {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(length = 128)
  private String subTitle;

  @Column(length = 512)
  private String description;

  @Column(length = 64)
  private String fileName;

  @Column(length = 512)
  private String code;
}
