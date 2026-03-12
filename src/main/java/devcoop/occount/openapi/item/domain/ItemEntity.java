package devcoop.occount.openapi.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "occount_items")
public class ItemEntity {

    @Id
    @Column(name = "itemId")
    private Integer itemId;

    @Column(name = "itemCode")
    private String itemCode;

    @Column(name = "itemName", nullable = false)
    private String itemName;

    @Column(name = "itemImage")
    private String itemImage;

    @Column(name = "itemExplain")
    private String itemExplain;

    @Column(name = "itemCategory")
    private String itemCategory;

    @Column(name = "itemPrice", nullable = false)
    private int itemPrice;

    @Column(name = "itemQuantity", nullable = false)
    private int itemQuantity;

    @Column(name = "event")
    private String event;

    @Column(name = "eventStartDate")
    private LocalDate eventStartDate;

    @Column(name = "eventEndDate")
    private LocalDate eventEndDate;

    protected ItemEntity() {
    }

    public ItemEntity(Integer itemId, String itemCode, String itemName, String itemImage, String itemExplain, String itemCategory, int itemPrice, int itemQuantity, String event, LocalDate eventStartDate, LocalDate eventEndDate) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemExplain = itemExplain;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.event = event;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getItemExplain() {
        return itemExplain;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getEvent() {
        return event;
    }

    public LocalDate getEventStartDate() {
        return eventStartDate;
    }

    public LocalDate getEventEndDate() {
        return eventEndDate;
    }
}
