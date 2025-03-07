package com.itany.nmms.entity;

public class Product {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_product.product_id
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    private Integer productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_product.product_no
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    private String productNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_product.name
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_product.price
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    private Double price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_product.image
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_product.product_type_id
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    private Integer productTypeId;

    private ProductType productType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_product.product_id
     *
     * @return the value of sys_product.product_id
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_product.product_id
     *
     * @param productId the value for sys_product.product_id
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_product.product_no
     *
     * @return the value of sys_product.product_no
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_product.product_no
     *
     * @param productNo the value for sys_product.product_no
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_product.name
     *
     * @return the value of sys_product.name
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_product.name
     *
     * @param name the value for sys_product.name
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_product.price
     *
     * @return the value of sys_product.price
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_product.price
     *
     * @param price the value for sys_product.price
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_product.image
     *
     * @return the value of sys_product.image
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_product.image
     *
     * @param image the value for sys_product.image
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_product.product_type_id
     *
     * @return the value of sys_product.product_type_id
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public Integer getProductTypeId() {
        return productTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_product.product_type_id
     *
     * @param productTypeId the value for sys_product.product_type_id
     *
     * @mbggenerated Sat Apr 20 15:58:09 CST 2024
     */
    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }


    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productNo='" + productNo + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", productTypeId=" + productTypeId +
                '}';
    }
}