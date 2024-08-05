package com.itgroup.foetida.controller;

import com.itgroup.foetida.Utility.Paging;
import com.itgroup.foetida.Utility.Utility;
import com.itgroup.foetida.bean.Mybs;
import com.itgroup.foetida.dao.MybsDao;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.image.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMybsController implements Initializable {
    private MybsDao dao = null;
    @FXML
    private ListView selectHl;
    @FXML
    private TableView<Mybs> bsugarTable;
    @FXML
    private Button btnFinish;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML private ImageView checkImage;

    //master
    //병원을 갈까 냉면을 먹을까...

    private String mode = null; //필드 검색을 위한 mode 변수(유틸리티 클래스에서 참조할 수 있게 인스턴스 레벨)
    private ObservableList<Mybs> dataList = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new MybsDao();

        setTableColumns();
        setPagination(0);

        // 현재 pageIndex 가 바뀌면 updatePage() 메서드를 호출하도록 이벤트 핸들러 등록
        pagination.currentPageIndexProperty().addListener(((observableValue, number, t1) -> updatePage()));

        List<String> categories = new ArrayList<>();
        categories.add("고혈당");
        categories.add("정상 혈당");
        categories.add("저혈당");
        ObservableList<String> dataList = FXCollections.observableArrayList(categories);
        selectHl.setItems(dataList);

        ChangeListener<String> listListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String bsugarmode) {
                System.out.println("oldValue : " + oldValue);
                System.out.println("newValue : " + bsugarmode);
                ObservableList tableData = FXCollections.observableArrayList(dao.selectedBsugar(bsugarmode));

                System.out.println(tableData);
                if (tableData != null) {
                    bsugarTable.setItems(tableData);
                }
            }
        };

        selectHl.getSelectionModel().selectedItemProperty().addListener(listListener);
        setContextMenu();
    }


    @FXML
    private Pagination pagination;

    private void setPagination(int pageIndex) {
        // 현재 페이지 번호와 바꾸려는 페이지 번호(pageIndex)가 같다면 updatePage()가 자동으로 안불린다
        // 수동호출
        if(pagination.getCurrentPageIndex() == pageIndex) {
            updatePage();
            return;
        }

        pagination.setCurrentPageIndex(pageIndex);

        // pagination.setPageFactory(this::createPage);
    }

    private void updatePage(){
        int totalCount = 0;
        totalCount = dao.getTotalCount();

        Paging pageInfo = new Paging(String.valueOf(pagination.getCurrentPageIndex() + 1), "10", totalCount, null);

        pagination.setPageCount(pageInfo.getTotalPage());
        fillTableData(pageInfo);
    }

    private Node createPage(Integer pageIndex) {
        int totalCount = 0;
        totalCount = dao.getTotalCount();

        Paging pageInfo = new Paging(String.valueOf(pageIndex + 1), "10", totalCount, null);

        pagination.setPageCount(pageInfo.getTotalPage());
        fillTableData(pageInfo);
        VBox vbox = new VBox(bsugarTable);
        return vbox;
    }

    @FXML
    private Label pageStatus;

    private void fillTableData(Paging pageInfo) {
        List<Mybs> mybsList = dao.getPaginationData(pageInfo);
        dataList = FXCollections.observableArrayList(mybsList);
        bsugarTable.setItems(dataList);
        pageStatus.setText(pageInfo.getPagingStatus());

    }

    private void setTableColumns() {
        String[] fields = {"dataid", "bsugar", "medate", "device", "binsulin", "react"};
        String[] colNames = {"기록번호", "혈당", "측정일시", "측정장비", "인슐린", "대처"};

        TableColumn tableColumn = null;

        for (int i = 0; i < fields.length; i++) {
            tableColumn = bsugarTable.getColumns().get(i);
            tableColumn.setText(colNames[i]);

            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));
            tableColumn.setStyle("-fx-alignment:center;"); // 모든 셀 데이터를 가운데 정렬하기
        }
    }


    public void onInsert(ActionEvent event) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "InsertBs.fxml";
        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load();
        Scene scene = new Scene(container); //씬에 담기
        Stage stage = new Stage();
        stage.setTitle("새 혈당 기록");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //씬을 무대에 담기
        stage.setResizable(false);
        stage.showAndWait();

        setPagination(0);
    }

    public void onUpdate(ActionEvent event) throws Exception {
        int idx = bsugarTable.getSelectionModel().getSelectedIndex();
        System.out.println(idx);

        if (idx >= 0) {
            System.out.println("선택된 항목 색인 : " + idx);

            String fxmlFile = Utility.FXML_PATH + "UpdateBs.fxml";
            //import java.net.URL;
            URL url = getClass().getResource(fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(url);

            Parent container = null;
            container = fxmlLoader.load();

            Mybs bean = bsugarTable.getSelectionModel().getSelectedItem();
            UpdateBsController controller = fxmlLoader.getController();
            controller.setBean(bean);
            //update 기능에서 추가된 내용 끝

            Scene scene = new Scene(container);
            Stage stage = new Stage();
            stage.setTitle("혈당 기록 수정");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

            setPagination(0);

        } else {
            String[] message = new String[]{"기록 선택 확인", "기록 미선택", "수정하려는 기록을 선택하세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void onDelete(ActionEvent event) {
        int idx = bsugarTable.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION)); //confirmation : yes or no
            alert.setTitle("삭제 확인");
            alert.setHeaderText("삭제 여부 선택");
            alert.setContentText("이 항목을 정말로 삭제합니까?");
            Optional<ButtonType> response = alert.showAndWait();

            if (response.get() == ButtonType.OK) {
                Mybs bean = bsugarTable.getSelectionModel().getSelectedItem();
                int dataid = bean.getDataid();
                int cnt = -1;
                cnt = dao.deleteData(dataid);
                if (cnt != -1) {
                    System.out.println("삭제 완료.");
                    setPagination(0);
                } else {
                    System.out.println("삭제 성공.");
                }
            } else {
                System.out.println("삭제 취소.");
            }

        } else {
            String[] message = new String[]{"기록 선택 확인", "기록 미선택", "삭제하려는 항목 선택하셈"};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }
    }

    private void setContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem01 = new MenuItem("당화혈색소 보기");
        MenuItem menuItem02 = new MenuItem("파일로 저장하기");
        MenuItem menuItem03 = new MenuItem("?");

        contextMenu.getItems().addAll(menuItem01, menuItem02, menuItem03);
        bsugarTable.setContextMenu(contextMenu);

        menuItem01.setOnAction((event -> {
            try{
                makeHba1c();
            }catch(Exception e){
                e.printStackTrace();
            }
        }));

        menuItem02.setOnAction(event -> {
            try{
                FileChooser chooser = new FileChooser();
                Window window = bsugarTable.getScene().getWindow() ;
                File savedFile = chooser.showSaveDialog(window);

                if(savedFile != null){
                    FileWriter fw = null;
                    BufferedWriter bw = null;

                    try{
                        fw = new FileWriter(savedFile);
                        bw = new BufferedWriter(fw); // 승급

                        for(Mybs bean : dataList){
                            bw.write(bean.toString());
                            bw.newLine(); // 엔터키
                        }

                        System.out.println("파일 저장이 완료되었습니다.");
                    }catch (Exception ex){
                        ex.printStackTrace();

                    }finally {
                        try{
                            if(bw!=null){bw.close();}
                            if(fw!=null){fw.close();}
                        }catch (Exception ex2){
                            ex2.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("파일 저장이 취소되었습니다.");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        });

        menuItem03.setOnAction(event -> {
            try{
                Runtime.getRuntime().exec("cmd /c start cmd");
//                easteregg();
                //깜짝 기능으로 컨텍스트 메뉴의 '?'를 선택하면 cmd를 통해 '카드게임.exe'를 호출하려 했으나 해당 실행파일 경로를 찾지 못해 구현 보류

            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }



    private void makeHba1c() throws Exception {
        System.out.println("당화혈색소 호출하기");
        FXMLLoader fxmlLoader = this.getFxmlLoader("hba1c.fxml");
        Parent parent = fxmlLoader.load();

        Hba1cController controller = fxmlLoader.getController();
        controller.makeH();
        this.showModal(parent);
    }

    private void showModal(Parent parent) {
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onClosing(ActionEvent event) {
        System.out.println("프로그램을 종료합니다.");
        Platform.exit();
    }

    private FXMLLoader getFxmlLoader(String fxmlName) throws Exception {
        Parent parent = null;
        String fileName = Utility.FXML_PATH + fxmlName;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        return fxmlLoader;
    }
}

