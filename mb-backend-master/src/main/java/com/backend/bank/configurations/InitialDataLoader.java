package com.backend.bank.configurations;

import com.backend.bank.common.Constants;
import com.backend.bank.model.Privilege;
import com.backend.bank.model.Role;
import com.backend.bank.model.User;
import com.backend.bank.repository.PrivilegeRepository;
import com.backend.bank.repository.RoleRepository;
import com.backend.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        //PERMISSION CATEGORIES
        createPrivilegeIfNotFound("ROLE_CREATE CATEGORY","ROLE_TẠO DANH MỤC","CATEGORY","DANH MỤC");

        createPrivilegeIfNotFound("ROLE_GET CATEGORY","ROLE_XEM DANH MỤC","CATEGORY","DANH MỤC");

        createPrivilegeIfNotFound("ROLE_EDIT CATEGORY","ROLE_CHỈNH SỬA DANH MỤC","CATEGORY","DANH MỤC");

        createPrivilegeIfNotFound("ROLE_UPDATE POSITION CATEGORY","ROLE_CHỈNH SỬA VỊ TRÍ DANH MỤC","CATEGORY","DANH MỤC");

        createPrivilegeIfNotFound("ROLE_DELETE CATEGORY","ROLE_XÓA DANH MỤC","CATEGORY","DANH MỤC");


        //permission Pages
        createPrivilegeIfNotFound("ROLE_GET PAGE","ROLE_XEM PAGE","PAGE","PAGE");

        createPrivilegeIfNotFound("ROLE_CREATE PAGE","ROLE_TẠO PAGE","PAGE","PAGE");

        createPrivilegeIfNotFound("ROLE_EDIT PAGE","ROLE_CHỈNH SỬA PAGE","PAGE","PAGE");

        createPrivilegeIfNotFound("ROLE_DELETE PAGE","ROLE_XÓA PAGE","PAGE","PAGE");

        createPrivilegeIfNotFound("ROLE_UPDATE POSITION PAGE","ROLE_CHỈNH SỬA VỊ TRÍ PAGE","PAGE","PAGE");

        createPrivilegeIfNotFound("ROLE_ACCEPT PAGES","ROLE_DUYỆT TRANG","PAGE","PAGE");

        //permission Slider
        createPrivilegeIfNotFound("ROLE_GET SLIDER","ROLE_XEM SLIDER","SLIDER","SLIDER");

        createPrivilegeIfNotFound("ROLE_CREATE SLIDER","ROLE_TẠO SLIDER","SLIDER","SLIDER");

        createPrivilegeIfNotFound("ROLE_EDIT SLIDER","ROLE_CHỈNH SỬA SLIDER","SLIDER","SLIDER");

        createPrivilegeIfNotFound("ROLE_DELETE SLIDER","ROLE_XÓA SLIDER","SLIDER","SLIDER");

        //permission Menu
        createPrivilegeIfNotFound("ROLE_GET MENU","ROLE_XEM MENU","MENU","MENU");

        createPrivilegeIfNotFound("ROLE_CREATE MENU","ROLE_TẠO MENU","MENU","MENU");

        createPrivilegeIfNotFound("ROLE_EDIT MENU","ROLE_CHỈNH SỬA MENU","MENU","MENU");

        createPrivilegeIfNotFound("ROLE_DELETE MENU","ROLE_XÓA MENU","MENU","MENU");

        //permission Menuitem
        createPrivilegeIfNotFound("ROLE_GET MENUITEM","ROLE_XEM MENUITEM","MENUITEM","MENUITEM");

        createPrivilegeIfNotFound("ROLE_CREATE MENUITEM","ROLE_TẠO MENUITEM","MENUITEM","MENUITEM");

        createPrivilegeIfNotFound("ROLE_EDIT MENUITEM","ROLE_CHỈNH SỬA MENUITEM","MENUITEM","MENUITEM");

        createPrivilegeIfNotFound("ROLE_DELETE MENUITEM","ROLE_XÓA MENUITEM","MENUITEM","MENUITEM");

        createPrivilegeIfNotFound("ROLE_UPDATE POSITION MENUITEM","ROLE_CHỈNH SỬA VỊ TRÍ MENUITEM","MENUITEM","MENUITEM");

        //permission Mailtemplate
        createPrivilegeIfNotFound("ROLE_GET MAILTEMPLATE","ROLE_XEM MAILTEMPLATE","MAILTEMPLATE","MAILTEMPLATE");

        createPrivilegeIfNotFound("ROLE_CREATE MAILTEMPLATE","ROLE_TẠO MAILTEMPLATE","MAILTEMPLATE","MAILTEMPLATE");

        createPrivilegeIfNotFound("ROLE_EDIT MAILTEMPLATE","ROLE_CHỈNH SỬA MAILTEMPLATE","MAILTEMPLATE","MAILTEMPLATE");

        createPrivilegeIfNotFound("ROLE_DELETE MAILTEMPLATE","ROLE_XÓA MAILTEMPLATE","MAILTEMPLATE","MAILTEMPLATE");


        //PERMISSION NEWS

        createPrivilegeIfNotFound("ROLE_CREATE NEWS","ROLE_TẠO TIN TỨC","NEWS","TIN TỨC");

        createPrivilegeIfNotFound("ROLE_GET NEWS","ROLE_XEM TIN TỨC","NEWS","TIN TỨC");

        createPrivilegeIfNotFound("ROLE_EDIT NEWS","ROLE_CHỈNH SỬA TIN TỨC","NEWS","TIN TỨC");

        createPrivilegeIfNotFound("ROLE_DELETE NEWS","ROLE_XÓA TIN TỨC","NEWS","TIN TỨC");

        createPrivilegeIfNotFound("ROLE_ACCEPT NEWS","ROLE_DUYỆT TIN TỨC","NEWS","TIN TỨC");

        //PERMISSION NETWORKS

        createPrivilegeIfNotFound("ROLE_GET NETWORKS","ROLE_XEM MẠNG LƯỚI","NETWORKS","MẠNG LƯỚI");

        createPrivilegeIfNotFound("ROLE_CREATE NETWORKS","ROLE_TẠO MẠNG LƯỚI","NETWORKS","MẠNG LƯỚI");

        createPrivilegeIfNotFound("ROLE_EDIT NETWORKS","ROLE_SỬA MẠNG LƯỚI","NETWORKS","MẠNG LƯỚI");

        createPrivilegeIfNotFound("ROLE_DELETE NETWORKS","ROLE_XÓA MẠNG LƯỚI","NETWORKS","MẠNG LƯỚI");


        //PERMISSION INTEREST_RATE

        createPrivilegeIfNotFound("ROLE_GET INTEREST_RATE","ROLE_XEM LÃI SUẤT","INTEREST_RATE","LÃI SUẤT");

        createPrivilegeIfNotFound("ROLE_CREATE INTEREST_RATE","ROLE_TẠO LÃI SUẤT","INTEREST_RATE","LÃI SUẤT");

        createPrivilegeIfNotFound("ROLE_EDIT INTEREST_RATE","ROLE_SỬA LÃI SUẤT","INTEREST_RATE","LÃI SUẤT");

        createPrivilegeIfNotFound("ROLE_DELETE INTEREST_RATE","ROLE_XÓA LÃI SUẤT","INTEREST_RATE","LÃI SUẤT");


        //PERMISSION EXCHANGE_RATE

        createPrivilegeIfNotFound("ROLE_GET EXCHANGE_RATE","ROLE_XEM TỈ GIÁ","EXCHANGE_RATE","TỈ GIÁ");

        createPrivilegeIfNotFound("ROLE_CREATE EXCHANGE_RATE","ROLE_TẠO TỈ GIÁ","EXCHANGE_RATE","TỈ GIÁ");

        createPrivilegeIfNotFound("ROLE_EDIT EXCHANGE_RATE","ROLE_SỬA TỈ GIÁ","EXCHANGE_RATE","TỈ GIÁ");

        createPrivilegeIfNotFound("ROLE_DELETE EXCHANGE_RATE","ROLE_XÓA TỈ GIÁ","EXCHANGE_RATE","TỈ GIÁ");

        //PERMISSION BLOCK

        createPrivilegeIfNotFound("ROLE_GET BLOCK","ROLE_XEM BLOCK","BLOCK","BLOCK");

        createPrivilegeIfNotFound("ROLE_ADD BLOCK","ROLE_THÊM BLOCK","BLOCK","BLOCK");

        createPrivilegeIfNotFound("ROLE_DELETE BLOCK","ROLE_XÓA BLOCK","BLOCK","BLOCK");

        createPrivilegeIfNotFound("ROLE_EDIT BLOCK","ROLE_SỬA BLOCK","BLOCK","BLOCK");

        //PERMISSION STORE

        createPrivilegeIfNotFound("ROLE_EDIT STORE","ROLE_SỬA STORE","STORE","STORE");

        createPrivilegeIfNotFound("ROLE_GET STORE","ROLE_XEM STORE","STORE","STORE");

        //PERMISSION THƯ PHẢN HỒI

        createPrivilegeIfNotFound("ROLE_GET FEEDBACK","ROLE_XEM THƯ PHẢN HỒI","FEEDBACK","THƯ PHẢN HỒI");

        createPrivilegeIfNotFound("ROLE_EDIT FEEDBACK","ROLE_SỬA THƯ PHẢN HỒI","FEEDBACK","THƯ PHẢN HỒI");


        //PERMISSION FORM

        createPrivilegeIfNotFound("ROLE_GET FORM","ROLE_XEM FORM","FORM","FORM");

        createPrivilegeIfNotFound("ROLE_ADD FORM","ROLE_THÊM FORM","FORM","FORM");

        createPrivilegeIfNotFound("ROLE_DELETE FORM","ROLE_XÓA FORM","FORM","FORM");

        createPrivilegeIfNotFound("ROLE_EDIT FORM","ROLE_SỬA FORM","FORM","FORM");

        //PERMISSION MEDIA

        createPrivilegeIfNotFound("ROLE_GET MEDIA","ROLE_XEM MEDIA","MEDIA","MEDIA");

        createPrivilegeIfNotFound("ROLE_ADD MEDIA","ROLE_THÊM MEDIA","MEDIA","MEDIA");

        createPrivilegeIfNotFound("ROLE_DELETE MEDIA","ROLE_XÓA MEDIA","MEDIA","MEDIA");

        createPrivilegeIfNotFound("ROLE_EDIT MEDIA","ROLE_SỬA MEDIA","MEDIA","MEDIA");

        //PERMISSION GROUP

        createPrivilegeIfNotFound("ROLE_GET GROUP","ROLE_XEM NHÓM","GROUP","NHÓM");

        createPrivilegeIfNotFound("ROLE_ADD GROUP","ROLE_THÊM NHÓM","GROUP","NHÓM");

        createPrivilegeIfNotFound("ROLE_DELETE GROUP","ROLE_XÓA NHÓM","GROUP","NHÓM");

        createPrivilegeIfNotFound("ROLE_EDIT GROUP","ROLE_SỬA NHÓM","GROUP","NHÓM");

        //PERMISSION SETTING

        createPrivilegeIfNotFound("ROLE_GET SETTING","ROLE_XEM CÀI ĐẶT","SETTING","SETTING");

        createPrivilegeIfNotFound("ROLE_EDIT SETTING","ROLE_SỬA CÀI ĐẶT","SETTING","SETTING");


        //PERMISSION USER

        createPrivilegeIfNotFound("ROLE_GET USER","ROLE_XEM NHÂN VIÊN","USER","NHÂN VIÊN");

        createPrivilegeIfNotFound("ROLE_ADD USER","ROLE_THÊM NHÂN VIÊN","USER","NHÂN VIÊN");

        createPrivilegeIfNotFound("ROLE_DELETE USER","ROLE_XÓA NHÂN VIÊN","USER","NHÂN VIÊN");

        createPrivilegeIfNotFound("ROLE_EDIT USER","ROLE_SỬA NHÂN VIÊN","USER","NHÂN VIÊN");

        //PERMISSION USER

        createPrivilegeIfNotFound("ROLE_GET ROLE","ROLE_XEM VAI TRÒ","ROLE","VAI TRÒ");

        createPrivilegeIfNotFound("ROLE_ADD ROLE","ROLE_THÊM VAI TRÒ","ROLE","VAI TRÒ");

        createPrivilegeIfNotFound("ROLE_DELETE ROLE","ROLE_XÓA VAI TRÒ","ROLE","VAI TRÒ");

        createPrivilegeIfNotFound("ROLE_EDIT ROLE","ROLE_SỬA VAI TRÒ","ROLE","VAI TRÒ");

        //PERMISSION TAG

        createPrivilegeIfNotFound("ROLE_GET TAG","ROLE_XEM TAG","TAG","TAG");

        createPrivilegeIfNotFound("ROLE_ADD TAG","ROLE_THÊM TAG","TAG","TAG");

        createPrivilegeIfNotFound("ROLE_DELETE TAG","ROLE_XÓA TAG","TAG","TAG");

        createPrivilegeIfNotFound("ROLE_EDIT TAG","ROLE_SỬA TAG","TAG","TAG");

        alreadySetup = true;
    }

    @Transactional
    public void createPrivilegeIfNotFound(String en,String vi, String groupEn, String groupVi) {
        if((!privilegeRepository.findByName(en).isPresent() && !privilegeRepository.findByName(vi).isPresent())) {
            Privilege privilege = new Privilege();
            privilege.setName(en);
            privilege.setLocal(Constants.EN);
            privilege.setGroupRole(groupEn);
            privilegeRepository.save(privilege);
            privilege.setPrivilegeId(privilege.getId());
            Privilege privilege1 = new Privilege();
            privilege1.setName(vi);
            privilege1.setLocal(Constants.VI);
            privilege1.setGroupRole(groupVi);
            privilege1.setPrivilegeId(privilege.getId());
            privilegeRepository.save(privilege1);
        }
    }

}
