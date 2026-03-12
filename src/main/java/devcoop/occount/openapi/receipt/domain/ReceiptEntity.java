package devcoop.occount.openapi.receipt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "occount_kioskReceipts")
public class ReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receiptId")
    private Long receiptId;

    @Column(name = "itemCode")
    private String itemCode;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "userCode")
    private String userCode;

    @Column(name = "saleQty", nullable = false)
    private int saleQty;

    @Column(name = "tradedPoint", nullable = false)
    private int tradedPoint;

    @Column(name = "saleType", nullable = false)
    private byte saleType;

    @Column(name = "saleDate")
    private LocalDate saleDate;

    @Column(name = "dailyNum", nullable = false)
    private int dailyNum;

    protected ReceiptEntity() {
    }

    public ReceiptEntity(Long receiptId, String itemCode, String itemName, String userCode, int saleQty, int tradedPoint, byte saleType, LocalDate saleDate, int dailyNum) {
        this.receiptId = receiptId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.userCode = userCode;
        this.saleQty = saleQty;
        this.tradedPoint = tradedPoint;
        this.saleType = saleType;
        this.saleDate = saleDate;
        this.dailyNum = dailyNum;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUserCode() {
        return userCode;
    }

    public int getSaleQty() {
        return saleQty;
    }

    public int getTradedPoint() {
        return tradedPoint;
    }

    public byte getSaleType() {
        return saleType;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public int getDailyNum() {
        return dailyNum;
    }
}
