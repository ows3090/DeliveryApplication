package ows.kotlinstudy.deliveryapplicaiton.data.response.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity

/**
 * @SerializedName : 직렬화 또는 역직렬화 시 사용되는 Json String Key Name
 * @Expose : 직렬화 또는 역직렬화를 할지 결정
 */
data class AddressInfo(
    @SerializedName("fullAddress")
    @Expose
    val fullAddress: String?,
    @SerializedName("addressType")
    @Expose
    val addressType: String?,
    @SerializedName("city_do")
    @Expose
    val cityDo: String?,
    @SerializedName("gu_gun")
    @Expose
    val guGun: String?,
    @SerializedName("eup_myun")
    @Expose
    val eupMyun: String?,
    @SerializedName("adminDong")
    @Expose
    val adminDong: String?,
    @SerializedName("adminDongCode")
    @Expose
    val adminDongCode: String?,
    @SerializedName("legalDong")
    @Expose
    val legalDong: String?,
    @SerializedName("legalDongCode")
    @Expose
    val legalDongCode: String?,
    @SerializedName("ri")
    @Expose
    val ri: String?,
    @SerializedName("bunji")
    @Expose
    val bunji: String?,
    @SerializedName("roadName")
    @Expose
    val roadName: String?,
    @SerializedName("buildingIndex")
    @Expose
    val buildingIndex: String?,
    @SerializedName("buildingName")
    @Expose
    val buildingName: String?,
    @SerializedName("mappingDistance")
    @Expose
    val mappingDistance: String?,
    @SerializedName("roadCode")
    @Expose
    val roadCode: String?
) {

    fun toSearchInfoEntity(locationLatLngEntity: LocationLatLngEntity) = MapSearchInfoEntity(
        fullAddress = fullAddress ?: "주소 정보 없음",
        name = buildingName ?: "빌딩 정보 없음",
        locationLatLng = locationLatLngEntity
    )
}