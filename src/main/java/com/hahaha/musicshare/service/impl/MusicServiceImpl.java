package com.hahaha.musicshare.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hahaha.musicshare.common.cache.RequestContext;
import com.hahaha.musicshare.common.config.OssConfig;
import com.hahaha.musicshare.common.exception.ErrorCode;
import com.hahaha.musicshare.common.exception.ServerException;
import com.hahaha.musicshare.mapper.MusicMapper;
import com.hahaha.musicshare.model.entity.Music;
import com.hahaha.musicshare.model.vo.MusicVO;
import com.hahaha.musicshare.service.MusicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    // 允许上传⽂件(图⽚)的格式
    static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg"
            , ".jpeg", ".gif", ".png"};
    // 定义允许上传的音乐文件的后缀名
    static final String[] browserSupportedExtensions = {"mp3", "wav", "aac", "ogg", "opus"};

    private final OssConfig ossConfig;

    @Override
    public List<MusicVO> getHostMusic(Integer num) {
        return baseMapper.Host(num);
    }

    @Override
    public List<MusicVO> getLatestMusic() {
        return baseMapper.Latest();
    }

    @Override
    public List<MusicVO> getMusicByAuthorId() {
        return baseMapper.myWorks(RequestContext.getUserId());
    }

    @Override
    public List<MusicVO> getMusicByKeyword(String keyword) {
        return baseMapper.searchMusicFromUser(keyword);
    }

    @Override
    public List<MusicVO> getMusicByKeyword(String keyword, int a) {
        return baseMapper.searchMusicFromAdmin(keyword);
    }

    @Override
    public List<MusicVO> getMusicNeedAudit() {
        return baseMapper.MusicForAdmin();
    }

    @Override
    public void shareMusic(MultipartFile cover, MultipartFile music, String song_name, String description, String keywords) {
//        TODO
        String Cover, Music;
        boolean isImage = false, isMusic = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(cover.getOriginalFilename(),
                    type)) {
                isImage = true;
                break;
            }
        }
        for (String type : browserSupportedExtensions) {
            if (StringUtils.endsWithIgnoreCase(music.getOriginalFilename(),
                    type)) {
                isMusic = true;
                break;
            }
        }
        if (!isMusic && !isImage) throw new ServerException("文件格式均不支持");
        if (!isImage) {
            throw new ServerException("图⽚格式不支持");
        } else if (!isMusic) {
            throw new ServerException("音乐格式不支持");
        }
        Cover = uploadFile(cover, "image/png");
        Music = uploadFile(music, "audio/mpeg");
        Music res = new Music();
        res.setSong_name(song_name);
        res.setAuthor_id(RequestContext.getUserId());
        res.setDescription(description);
        res.setLink(Music);
        res.setCover(Cover);
        res.setKeywords(keywords);
        baseMapper.insert(res);
    }

    @Override
    public void updateMusicStatus(Integer music_id, String status) {
        Music music = new Music();
        music.setStatus(status);
        if (baseMapper.updateById(music) < 1) {
            throw new ServerException(ErrorCode.OPERATION_FAIL);
        }
    }

    @Override
    public void clickMusic(Integer music_id) {
        Music music = baseMapper.selectById(music_id);
        music.setClicks(music.getClicks() + 1);
    }

    private String uploadFile(MultipartFile file, String type) {
        // 获取⽂件原名称
        String originalFilename = file.getOriginalFilename();
        // 获取⽂件类型
        assert originalFilename != null;
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新⽂件名称
        String newFileName = UUID.randomUUID() + fileType;
        // 构建⽇期路径, 例如：OSS⽬标⽂件夹/2024/04/31/⽂件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        // ⽂件上传的路径地址
        String uploadUrl = filePath + "/" + newFileName;
        // 获取⽂件输⼊流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(type);
        //读取配置⽂件中的属性
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        String bucketName = ossConfig.getBucketName();
        // 创建 OssClient
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId
                , accessKeySecret);
        //⽂件上传⾄阿⾥云OSS
        ossClient.putObject(bucketName, uploadUrl, inputStream, meta);
        // 获取⽂件上传后的图⽚返回地址
        return "https://" + bucketName + "." + endpoint + "/" + uploadUrl;
    }
}
