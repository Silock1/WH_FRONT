package com.warehouse_accounting.components.purchases;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.warehouse_accounting.models.dto.StatusDto;
import com.warehouse_accounting.services.impl.StatusServiceImpl;
import com.warehouse_accounting.services.interfaces.StatusService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@CssImport(value = "./css/GridForStatus.css", themeFor = "vaadin-*-overlay")
public class GridForStatus extends Grid<StatusDto> {

    private List<StatusDto> statusLineList;
    private List<StatusDto> removedStatusLineList = new ArrayList<>();
    private Grid<StatusDto> grid = new Grid<>(StatusDto.class, false);
    private StatusService statusService;
    private Retrofit retrofit;
    private StatusService statusServiceImpl;
    private Long availableIndex;
    private String nameOfClass;
    private HorizontalLayout emptyFieldErrorLayout;
    private Dialog statusDialog;


    public GridForStatus( StatusService statusService, String nameOfClass) {
        this.statusService = statusService;
        this.nameOfClass = nameOfClass;

        Div text = new Div(
                new Text("\t\n" +
                        "Вы находитесь на странице настройки статусов \n" +
                        "документов. При помощи статусов вы можете\n" +
                        "отслеживать жизненный цикл своих документов. \n" +
                        "Например, заказы покупателей могут проходить \n" +
                        "через статусы «Принят», «Оплачен», «Отгружен»."));


        statusLineList = statusService.getAllByNameOfClass(nameOfClass);

        statusLineList.sort(Comparator.comparing(StatusDto::getId));
        refreshGrid();
        statusDialog = new Dialog();
        statusDialog.setWidth("50%");
        statusDialog.setHeight("100%");
        statusDialog.getElement().setAttribute("theme", "dialog-top-right");
        Button closeButton = new Button("Закрыть", new Icon(VaadinIcon.CLOSE_SMALL));
        closeButton.getStyle().set("background-color", "white").set("color", "grey").set("size", "22px").set("text-decoration", "underline");
        closeButton.setIconAfterText(true);
        Button saveButton = new Button("Сохранить");
        saveButton.getStyle().set("background-color", "#aec612").set("color", "white").set("size", "22px");
        Button cancelButton = new Button("Отменить");
        cancelButton.getStyle().set("background-color", "#ededed").set("color", "black").set("size", "22px");
        cancelButton.addClickListener(es -> {
            statusDialog.close();
            cleanGrid();
        });
        closeButton.addClickListener(es -> {
            statusDialog.close();
            cleanGrid();
        });

        saveButton.addClickListener(esp -> {
            checkIfEmpty();
        });
        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE_O));
        helpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        helpButton.getStyle().set("background-color", "#edf8ff").set("size", "30px");
        helpButton.addClickListener(buttonClickEvent -> {
            Button closeNotButton = new Button(new Icon(VaadinIcon.CLOSE));
            closeNotButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            Dialog notDialog = new Dialog();

            closeNotButton.addClickListener(event -> {
                notDialog.close();
            });
            HorizontalLayout layout = new HorizontalLayout(text, closeNotButton);
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            notDialog.add(layout);
            notDialog.open();
            notDialog.setCloseOnOutsideClick(true);
            notDialog.getElement().setAttribute("theme", "dialog-notification");
        });
        statusDialog.add(closeButton);
        Label settingsLabel = new Label("Настройки статусов");
        settingsLabel.getStyle().set("font-size", "24px");
        Label emptyFieldErrorLabel = new Label("Название не может быть пустым");
        emptyFieldErrorLabel.getStyle().set("font-size", "16px");
        Icon closeErrorIcon = new Icon(VaadinIcon.CLOSE_CIRCLE);
        closeErrorIcon.getStyle().set("color", "#a8302a").set("size", "0px").set("margin-left", "58px");
        closeErrorIcon.setSize("16px");
        emptyFieldErrorLayout = new HorizontalLayout(closeErrorIcon, emptyFieldErrorLabel);
        emptyFieldErrorLayout.getStyle().set("margin-bottom", "12px");
        emptyFieldErrorLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        statusDialog.add(new HorizontalLayout(helpButton, settingsLabel));
        HorizontalLayout saveCancelLayout = new HorizontalLayout(saveButton, cancelButton);
        saveCancelLayout.getStyle().set("margin-bottom", "15px").set("margin-top", "12px").set("margin-left", "45px");
        statusDialog.add(saveCancelLayout);
        statusDialog.add(emptyFieldErrorLayout);
        emptyFieldErrorLayout.setVisible(false);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, statusLine) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(esd -> this.moveUp(statusLine));
                    button.setIcon(new Icon(VaadinIcon.CARET_UP));
                })).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, statusLine) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(esd -> this.moveDown(statusLine));
                    button.getStyle().set("width", "2px").set("padding", "0px").set("margin", "0px");
                    button.setIcon(new Icon(VaadinIcon.CARET_DOWN));
                })).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(
                new ComponentRenderer<>(TextField::new, (titleField, statusLine) -> {
                    titleField.getStyle().set("width", "300px");
                    titleField.getStyle().set("background-color", "#ffffff");
                    titleField.setValueChangeMode(ValueChangeMode.LAZY);
                    titleField.setValue(statusLine.getTitleOfStatus() == null ? "" : statusLine.getTitleOfStatus());
                    titleField.setRequired(true);
                    titleField.addValueChangeListener(content -> {
                        statusLine.setTitleOfStatus(titleField.getValue());
                    });

                })).setWidth("300px").setFlexGrow(0);
        grid.addComponentColumn(gridItem -> {

            MenuBar menuBar = new MenuBar();

            menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON);
            Icon menubarIcon = new Icon(VaadinIcon.CARET_DOWN);
            menubarIcon.getStyle().set("color", "white");
            MenuItem btnMenu = menuBar.addItem(menubarIcon);
            Button button1 = new Button();
            Icon icon1 = new Icon(VaadinIcon.STOP);
            icon1.getStyle().set("color", "#9a9a9a");
            icon1.setSize("34px");
            button1.setIcon(icon1);
            button1.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button2 = new Button();
            Icon icon2 = new Icon(VaadinIcon.STOP);
            icon2.getStyle().set("color", "#ea2412");
            icon2.setSize("34px");
            button2.setIcon(icon2);
            button2.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button3 = new Button();
            Icon icon3 = new Icon(VaadinIcon.STOP);
            icon3.getStyle().set("color", "#e7820e");
            icon3.setSize("34px");
            button3.setIcon(icon3);
            button3.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button4 = new Button();
            Icon icon4 = new Icon(VaadinIcon.STOP);
            icon4.getStyle().set("color", "#694a18");
            icon4.setSize("34px");
            button4.setIcon(icon4);
            button4.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button5 = new Button();
            Icon icon5 = new Icon(VaadinIcon.STOP);
            icon5.getStyle().set("color", "#b6d640");
            icon5.setSize("34px");
            button5.setIcon(icon5);
            button5.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button6 = new Button();
            Icon icon6 = new Icon(VaadinIcon.STOP);
            icon6.getStyle().set("color", "#a3c710").set("background-color", "white");
            icon6.setSize("34px");
            button6.setIcon(icon6);
            button6.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button7 = new Button();
            Icon icon7 = new Icon(VaadinIcon.STOP);
            icon7.getStyle().set("color", "#87ac60").set("background-color", "white");
            icon7.setSize("34px");
            button7.setIcon(icon7);
            button7.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button8 = new Button();
            Icon icon8 = new Icon(VaadinIcon.STOP);
            icon8.getStyle().set("color", "#008836").set("background-color", "white");
            icon8.setSize("34px");
            button8.setIcon(icon8);
            button8.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button9 = new Button();
            Icon icon9 = new Icon(VaadinIcon.STOP);
            icon9.getStyle().set("color", "#86c7df").set("background-color", "white");
            icon9.setSize("34px");
            button9.setIcon(icon9);
            button9.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button10 = new Button();
            Icon icon10 = new Icon(VaadinIcon.STOP);
            icon10.getStyle().set("color", "#00a0e4").set("background-color", "white");
            icon10.setSize("34px");
            button10.setIcon(icon10);
            button10.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button11 = new Button();
            Icon icon11 = new Icon(VaadinIcon.STOP);
            icon11.getStyle().set("color", "#407182").set("background-color", "white");
            icon11.setSize("34px");
            button11.setIcon(icon11);
            button11.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button12 = new Button();
            Icon icon12 = new Icon(VaadinIcon.STOP);
            icon12.getStyle().set("color", "#00479b").set("background-color", "white");
            icon12.setSize("34px");
            button12.setIcon(icon12);
            button12.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button13 = new Button();
            Icon icon13 = new Icon(VaadinIcon.STOP);
            icon13.getStyle().set("color", "#ed61a0").set("background-color", "white");
            icon13.setSize("34px");
            button13.setIcon(icon13);
            button13.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button14 = new Button();
            Icon icon14 = new Icon(VaadinIcon.STOP);
            icon14.getStyle().set("color", "#a666be").set("background-color", "white");
            icon14.setSize("34px");
            button14.setIcon(icon14);
            button14.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button15 = new Button();
            Icon icon15 = new Icon(VaadinIcon.STOP);
            icon15.getStyle().set("color", "#8e0c2c").set("background-color", "white");
            icon15.setSize("34px");
            button15.setIcon(icon15);
            button15.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");
            Button button16 = new Button();
            Icon icon16 = new Icon(VaadinIcon.STOP);
            icon16.getStyle().set("color", "#000000").set("background-color", "white");
            icon16.setSize("34px");
            button16.setIcon(icon16);
            button16.getStyle().set("background-color", "white").set("padding", "0px").set("margin", "0px").set("spacing", "0px");

            String CONST = "0px";
            menuBar.getStyle().set("spacing", "0px").set("margin", "0px").set("padding", "0px");
            menuBar.getStyle().set("background-color", gridItem.getColorCode() == null ? "#9a9a9a" : gridItem.getColorCode());

            button1.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#9a9a9a");
                gridItem.setColorCode("#9a9a9a");
            });
            button2.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#ea2412");
                gridItem.setColorCode("#ea2412");
            });
            button3.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#e7820e");
                gridItem.setColorCode("#e7820e");
            });
            button4.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#694a18");
                gridItem.setColorCode("#694a18");
            });
            button5.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#b6d640");
                gridItem.setColorCode("#b6d640");
            });
            button6.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#a3c710");
                gridItem.setColorCode("#a3c710");
            });
            button7.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#87ac60");
                gridItem.setColorCode("#87ac60");
            });
            button8.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#008836");
                gridItem.setColorCode("#008836");
            });
            button9.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#86c7df");
                gridItem.setColorCode("#86c7df");
            });
            button10.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#00a0e4");
                gridItem.setColorCode("#00a0e4");
            });
            button11.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#407182");
                gridItem.setColorCode("#407182");
            });
            button12.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#00479b");
                gridItem.setColorCode("#00479b");
            });
            button13.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#ed61a0");
                gridItem.setColorCode("#ed61a0");
            });
            button14.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#a666be");
                gridItem.setColorCode("#a666be");
            });
            button15.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#8e0c2c");
                gridItem.setColorCode("#8e0c2c");
            });
            button16.addClickListener((eeee) -> {
                menuBar.getStyle().set("background-color", "#000000");
                gridItem.setColorCode("#000000");
            });

            HorizontalLayout hl1 = new HorizontalLayout(button1, button2, button3, button4);
            hl1.getStyle().set("spacing", CONST).set("background-color", "white").set("padding", "0px").set("margin", "0px");
            hl1.setSpacing(false);
            hl1.setMargin(false);
            hl1.setPadding(false);
            HorizontalLayout hl2 = new HorizontalLayout(button5, button6, button7, button8);
            hl2.getStyle().set("spacing", CONST).set("background-color", "white").set("padding", "0px").set("margin", "0px");
            hl2.setSpacing(false);
            hl2.setMargin(false);
            hl2.setPadding(false);
            HorizontalLayout hl3 = new HorizontalLayout(button9, button10, button11, button12);
            hl3.getStyle().set("spacing", CONST).set("background-color", "white").set("padding", "0px").set("margin", "0px");
            hl3.setSpacing(false);
            hl3.setMargin(false);
            hl3.setPadding(false);
            HorizontalLayout hl4 = new HorizontalLayout(button13, button14, button15, button16);
            hl4.getStyle().set("spacing", CONST).set("background-color", "white").set("padding", "0px").set("margin", "0px");
            hl4.setSpacing(false);
            hl4.setMargin(false);
            hl4.setPadding(false);
            VerticalLayout vl = new VerticalLayout(hl1, hl2, hl3, hl4);
            vl.getStyle().set("spacing", CONST).set("color", "white").set("background-color", "white").set("padding", "0px").set("margin", "0px");
            vl.setSpacing(false);
            vl.setMargin(false);
            vl.setPadding(false);
            MenuItem firstMenuItem = btnMenu.getSubMenu().addItem(vl);
            firstMenuItem.setEnabled(true);
            menuBar.getStyle().set("spacing", CONST).set("padding", "0px").set("margin", "0px");
            return menuBar;

        }).setWidth("47px").setFlexGrow(0);

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, statusLine) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(esd -> removeRow(statusLine));
                    Icon closeIcon = new Icon(VaadinIcon.CLOSE_CIRCLE);
                    closeIcon.setColor("grey");
                    button.setIcon(closeIcon);
                })).setAutoWidth(true).setFlexGrow(0);
        Button addStatusButton = new Button("Статус", new Icon(VaadinIcon.PLUS));
        addStatusButton.addClickListener((eas) -> {
            addRow();
        });
        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addThemeVariants(GridVariant.LUMO_COMPACT);
        grid.setHeightByRows(true);
        grid.setWidth("480");
        grid.getStyle().set("margin-left", "45px").set("background-color", "white");

        statusDialog.getElement().getStyle().set("padding", "20px");
        statusDialog.add(grid);
        statusDialog.add(addStatusButton);
        addStatusButton.getStyle().set("margin-top", "15px").set("margin-left", "55px").set("size", "22px").set("background-color", "white");
        statusDialog.addDialogCloseActionListener(y -> {
            if (statusDialog.isCloseOnOutsideClick()) {
                statusDialog.close();
                cleanGrid();
            }
        });
        statusDialog.open();
    }

    private void addRow() {
        if (availableIndex == null) {
            availableIndex = (statusService.getAll().size()) == 0 ? 1 : statusService.getAll().get(statusService.getAll().size() - 1).getId() + 1;
        }
        StatusDto statusDto = new StatusDto();
        statusDto.setId(availableIndex);
        statusDto.setNameOfClass(nameOfClass);
        statusLineList.add(statusDto);
        availableIndex++;
        this.refreshGrid();
    }

    private void checkIfEmpty() {
        int emptyCheck = 0;
        for (int i = 0; i < statusLineList.size(); i++) {
            if (statusLineList.get(i).getTitleOfStatus() == null || statusLineList.get(i).getTitleOfStatus().isEmpty()) {
                emptyCheck++;
            }
        }
        if (emptyCheck != 0) {
            emptyFieldErrorLayout.setVisible(true);
        } else {
            statusDialog.close();
            saveGrid();
            cleanGrid();
            UI.getCurrent().getPage().reload();

        }
    }
    private void cleanGrid() {
        statusLineList.clear();
        grid.removeAllColumns();
        grid.getDataProvider().refreshAll();
        availableIndex = null;
        removedStatusLineList = new ArrayList<>();
    }

    private void moveUp(StatusDto statusLine) {
        if (statusLineList.indexOf(statusLine) - 1 > -1) {
            StatusDto currentPositionStatusDto = statusLine;
            StatusDto liftedPositionStatusDto = statusLineList.get(statusLineList.indexOf(statusLine) - 1);
            Long currentPositionPosIndex = statusLineList.get(statusLineList.indexOf(statusLine) - 1).getId();
            Long liftedPositionPosIndex = statusLineList.get(statusLineList.indexOf(statusLine)).getId();
            currentPositionStatusDto.setId(currentPositionPosIndex);
            liftedPositionStatusDto.setId(liftedPositionPosIndex);
            statusLineList.set(statusLineList.indexOf(currentPositionStatusDto) - 1, currentPositionStatusDto);
            statusLineList.set(statusLineList.indexOf(currentPositionStatusDto) + 1, liftedPositionStatusDto);
            this.refreshGrid();
        }
    }

    private void moveDown(StatusDto statusLine) {
        if (statusLineList.indexOf(statusLine) + 1 < statusLineList.size()) {
            StatusDto currentPositionStatusDto = statusLine;
            StatusDto downPositionStatusDto = statusLineList.get(statusLineList.indexOf(statusLine) + 1);
            Long currentPositionPosIndex = statusLineList.get(statusLineList.indexOf(statusLine) + 1).getId();
            Long downPositionPosIndex = statusLineList.get(statusLineList.indexOf(statusLine)).getId();
            currentPositionStatusDto.setId(currentPositionPosIndex);
            downPositionStatusDto.setId(downPositionPosIndex);
            statusLineList.set(statusLineList.indexOf(currentPositionStatusDto) + 1, currentPositionStatusDto);
            statusLineList.set(statusLineList.indexOf(currentPositionStatusDto), downPositionStatusDto);
            this.refreshGrid();
        }
    }

    private void saveGrid() {

        List<StatusDto> statusesInBD = statusService.getAllByNameOfClass(nameOfClass);
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:4446")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            statusServiceImpl = new StatusServiceImpl("/api/statuses", retrofit);
            for (int i = 0; i < statusLineList.size(); i++) {
                StatusDto statusDto = new StatusDto();
                statusDto.setNameOfClass(statusLineList.get(i).getNameOfClass());
                statusDto.setColorCode(statusLineList.get(i).getColorCode());
                statusDto.setTitleOfStatus(statusLineList.get(i).getTitleOfStatus());
                statusDto.setId(statusLineList.get(i).getId());
                if (statusesInBD.size() != 0 && statusesInBD.get(statusesInBD.size() - 1).getId() >= (statusLineList.get(i)).getId()) {
                    statusServiceImpl.update(statusDto);
                } else {
                    statusServiceImpl.create(statusDto);
                }
            }
            for (StatusDto st : removedStatusLineList) {
                statusServiceImpl.deleteById(st.getId());
            }
            emptyFieldErrorLayout.setVisible(false);
        } catch (Exception ex) {
            Notification notification = Notification.show("Ошибка сохранения Статуса");
        }
    }

    private void refreshGrid() {
        if (statusLineList.size() > 0) {
            grid.setItems(statusLineList);
            grid.setVisible(true);
            grid.getDataProvider().refreshAll();
        } else {
            grid.setVisible(false);
        }
    }

    private void removeRow(StatusDto statusLine) {
        removedStatusLineList.add(statusLine);
        statusLineList.remove(statusLine);
        this.refreshGrid();
    }
}
