# <a name="_toc182235354"></a><a name="_toc182235462"></a>**TÀI LIỆU HƯỚNG DẪN HÓA ĐƠN ĐIỆN TỬ TÍCH HỢP (bản demo của API)**

# <a name="_toc182235355"></a><a name="_toc182235463"></a>**MỤC LỤC**
[TÀI LIỆU HƯỚNG DẪN HÓA ĐƠN ĐIỆN TỬ TÍCH HỢP (bản demo của API)	1](#_toc182235462)

[MỤC LỤC	2](#_toc182235463)

[1.	Các yếu tố môi trường của hệ thống (khi thực hiện tích hợp hóa đơn điện tử):	4](#_toc182235464)

[2.	Nguyên lý hoạt động của chức năng	4](#_toc182235465)

[3.	Thông tin chi tiết về project code	5](#_toc182235466)

[3.1	Nghiệp vụ OM:	5](#_toc182235467)

[3.1.1	General info (thông tin chung của hóa đơn):	5](#_toc182235468)

[3.1.2	Seller info (thông tin người bán):	7](#_toc182235469)

[3.1.3	buyer info ( thông tin người mua):	9](#_toc182235470)

[3.1.4	Payments info (thông tin thanh toán):	11](#_toc182235471)

[3.1.5	item info (thông tin đơn hàng):	11](#_toc182235472)

[3.1.6	taxBreakdowns (thông tin tổng hợp thuế suất):	13](#_toc182235473)

[3.1.7	summarize info ( thông tin tổng hợp tiền hàng cho toàn bộ hóa đơn):	13](#_toc182235474)

[3.1.8	meta data( Thông tin trường bổ sung):	14](#_toc182235475)

[3.1.9	meterReading (Thông tin đặc thù cho riêng hóa đơn điện/nước):	14](#_toc182235476)

[3.1.10	invoice File (file đính kèm khi lập hóa đơn):	14](#_toc182235477)

[3.1.11	qrcode:	14](#_toc182235478)

[3.1.12	fuelReading:	14](#_toc182235479)

[3.1.13	Hàm tạo chuỗi json:	14](#_toc182235480)

[3.2	Class gọi file jar chức năng hóa đơn:	14](#_toc182235481)

[4.	Thông tin về các chức năng:	15](#_toc182235482)

[4.1	Danh sách method_Id:	15](#_toc182235483)

[4.2 Chức năng ở mục OM:	16](#_toc182235484)

[4.3 Chức năng ở mục AR:	17](#_toc182235485)

[4.4 Chức năng mục INV:	18](#_toc182235486)

[4.5 Chức năng mục PO:	20](#_toc182235487)

[4.6 Thông tin in hóa đơn:	21](#_toc182235488)


**\

1. ## <a name="_toc182235356"></a><a name="_toc182235464"></a>Các yếu tố môi trường của hệ thống (khi thực hiện tích hợp hóa đơn điện tử):
- JDK 1.6
- Indigo Eclipse
- Oracle Database 11.1.0.7

Thông tin đăng nhập hóa đơn điện tử Viettel (demo):

- Địa chỉ: [viettel vinvoice](https://vinvoice.viettel.vn/)
- Tài khoản: 0100109106-507
- Mật khẩu: 2wsxCDE#

Đây là thông tin tài khoản hóa đơn điện tử demo của Viettel, em để ở đây để mình có thể đăng nhập vào và test.
1. ## <a name="_toc182235357"></a><a name="_toc182235465"></a>Nguyên lý hoạt động của chức năng
Các bước thực hiện tổng quát

![A group of white rectangular boxes with black text&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.001.png)









Các nguyên lý chạy của chức năng

![A screenshot of a chat&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.002.png)
1. ## <a name="_toc182235358"></a><a name="_toc182235466"></a>Thông tin chi tiết về project code
Lưu ý: 

- Mỗi nghiệp vụ là một project khác nhau, vì hiện tại có 4 nghiệp vụ nên có 4 project được cấu hình riêng cho mỗi nghiệp vụ.
- Project code có 2 phần chính là code về chức năng hóa đơn để xuất ra file jar và code về class gọi file jar được xuất ra

Thư viện cần thiết:

- OJDBC5
- Common-io 2.2
- Json-20140107
- Common-codec-1.6
- Jdic-all
- Jdk1.6 (dùng làm môi trường chạy và thử nghiệm)

Sử dụng nghiệp vụ OM làm ví dụ, các nghiệp vụ khác sẽ khác nhau vì nghie
1. ### <a name="_toc182235359"></a><a name="_toc182235467"></a>Nghiệp vụ OM:
Tham khảo chương 6 của tài liệu mô tả webservice hóa đơn điện tử Viettel
1. #### <a name="_toc182235468"></a>General info (thông tin chung của hóa đơn):
Bảng thông tin API Viettel yêu cầu:

|Tên cột|Cột ở database|Chi tiết|
| :- | :- | :- |
|transactionUuid|<p>TRANSACTION\_NUMBER</p><p>Table: om.om\_invoice\_headers</p>||
|invoiceType|<p>INVOICE\_TYPE</p><p>Table: om.om\_invoice\_headers	</p>|Chuyển từ dạng chữ sang dạng số|
|templateCode|<p>1/00834</p><p>Table: om.om\_invoice\_headers</p>|Default theo demo|
|invoiceSeries|<p>K24TGM</p><p>Table: om.om\_invoice\_headers</p>|Default theo demo|
|currencyCode|<p>CURRENCY\_CODE</p><p>Table: om.om\_invoice\_headers</p>||
|exchangeRate|<p>RATE	</p><p>Table: om.om\_invoice\_headers</p>||
|adjustmentType|1|Tùy chỉnh|
|paymentStatus|<p>STATUS</p><p>Table: ar.ar\_invoice\_headers</p>|Trạng thái trên hệ thống là Complete và Incomplete chuyển đổi thành true và false|
|cusGetInvoiceRight|True|Tùy chỉnh|

1. #### <a name="_toc182235469"></a>Seller info (thông tin người bán):

|Tên trường|Cột ở database|Mô tả|
| :- | :- | :- |
|sellerLegalName|<p>VS\_SITE\_NAME</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerTaxCode|0100109106-507|Default theo demo|
|sellerAddressLine|<p>ADDRESS\_LINE1</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerPhoneNumber|<p>PHONE</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerFaxNumber|<p>FAX	</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerEmail|<p>EMAIL</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerBankName|||
|sellerBankAccount|<p>BANK\_NAME</p><p>Table: hr.sys\_master\_vs\_banks</p>||
|sellerDistrictName|<p>BANK\_ACCOUNT\_NUM</p><p>Table: hr.sys\_master\_vs\_banks</p>||
|sellerCityName|<p>CITY</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerCountryCode|<p>COUNTRY\_CODE</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|sellerWebsite|verp.com.vn||
1. #### <a name="_toc182235470"></a>buyer info ( thông tin người mua):

|Tên trường|Cột ở database|Mô tả|
| :- | :- | :- |
|buyerLegalName|<p>VS\_SITE\_NAME</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerTaxCode|0101925883|Default theo demo|
|buyerAddressLine|<p>ADDRESS\_LINE1</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerPostalCode|<p>ZIP</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerCityName|||
|buyerPhoneNumber|<p>PHONE</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerFaxNumber|<p>FAX	</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerEmail|<p>EMAIL</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerBankName|<p>BANK\_NAME</p><p>Table: hr.sys\_master\_vs\_banks</p>||
|buyerBankAccount|<p>BANK\_ACCOUNT\_NUM</p><p>Table: hr.sys\_master\_vs\_banks</p>||
|buyerDistrictName|||
|buyerCityName|<p>CITY</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
|buyerCountryCode|<p>COUNTRY\_CODE</p><p>table: hr.sys\_master\_site\_ven\_cus</p>||
1. #### <a name="_toc182235471"></a>Payments info (thông tin thanh toán):

|Tên trường|Cột ở database|Chi tiết|
| :- | :- | :- |
|paymentMethod|2|Default, nếu khác xem trong tài liệu mô tả  của Viettel|
|paymentMethodName|CK||
1. #### <a name="_toc182235472"></a>item info (thông tin đơn hàng):

|Tên trường|Cột ở database|Chi tiết|
| :- | :- | :- |
|lineNumber|||
|itemCode|<p>ITEM\_ID</p><p>Table: om.om\_invoice\_lines</p>||
|itemName|<p>ITEM\_NAME</p><p>Table: om.om\_invoice\_lines</p>||
|unitName|<p>UOM</p><p>Table: om.om\_invoice\_lines</p>||
|itemNote|||
|unitPrice|<p>PRICE</p><p>Table: om.om\_invoice\_lines</p>||
|quantity|||
|itemTotalAmountWithoutTax|<p>AMOUNT</p><p>Table: om.om\_invoice\_lines</p>||
|itemTotalAmountWithTax|||
|itemTotalAmountAfterDiscount|||
|taxPercentage|<p>RATE</p><p>Table:</p><p>po.po\_taxes</p>||
|taxAmount|<p>TAX\_AMOUNT</p><p>Table: om.om\_invoice\_lines</p>||
|customTaxAmount|||
|discount|||
|itemDiscount|<p>DISCOUNT\_AMOUNT</p><p>Table: om.om\_invoice\_lines</p>||
1. #### <a name="_toc182235473"></a>taxBreakdowns (thông tin tổng hợp thuế suất):

|Tên trường|Cột ở database|Chi tiết|
| :- | :- | :- |
|taxPercentage|<p>taxPercentage</p><p>from: item info</p>||
|taxableAmount|<p>itemTotalAmountWithoutTax</p><p>from: item info</p>||
|taxAmount|<p>taxAmount</p><p>from: item info</p>||
1. #### <a name="_toc182235474"></a>summarize info ( thông tin tổng hợp tiền hàng cho toàn bộ hóa đơn):

|Tên trường|Cột ở database|Chi tiết|
| :- | :- | :- |
|sumOfTotalLineAmountWithoutTax|||
|totalAmountWithoutTax|||
|totalTaxAmount|||
|totalAmountWithTax|||
|totalAmountAfterDiscount|||
|discountAmount|||
|extraName|||
|extraValue|||
1. #### <a name="_toc182235475"></a>meta data( Thông tin trường bổ sung):
1. #### <a name="_toc182235476"></a>meterReading (Thông tin đặc thù cho riêng hóa đơn điện/nước):
1. #### <a name="_toc182235477"></a>invoice File (file đính kèm khi lập hóa đơn):
1. #### <a name="_toc182235478"></a>qrcode:
1. #### <a name="_toc182235479"></a>fuelReading:
1. #### <a name="_toc182235480"></a>Hàm tạo chuỗi json:
- Hàm json thông tin hóa đơn:
  - Tên: invoice\_draft
  - API sử dụng: lập hóa đơn nháp, xem trước hóa đơn nháp và phát hành hóa đơn.
- Hàm json thông tin hóa đơn:
  - Tên: invoice
  - API sử dụng: xem hóa đơn đã phát hành.
  1. ### <a name="_toc182235360"></a><a name="_toc182235481"></a>Class gọi file jar chức năng hóa đơn:
Class này được tạo lên với các hàm gọi file jar chạy trên môi trường jdk 1.6 thông qua command prompt.

Mẫu cmd trong code: 

String[] command = { "cmd", "/k",

`				`"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0\_45\\bin\\java.exe",

`				`"-jar", "E:\\FORMS\\EINVOICE\\OM\_INVOICE.jar", header\_id, method\_id };

Đường dẫn đến jdk1.6: 

"E:\\FORMS\\EINVOICE\\plugin\\jdk1.6.0\_45\\bin\\java.exe"

Đường dẫn đến file jar được xuất ra: "E:\\FORMS\\EINVOICE\\OM\_INVOICE.jar"

Thông số đưa vào: header\_id, method\_id
1. ## <a name="_toc182235361"></a><a name="_toc182235482"></a>Thông tin về các chức năng:
   1. ### <a name="_toc182235362"></a><a name="_toc182235483"></a>Danh sách method\_Id:

|method\_id|Chức năng|
| :- | :- |
|1|Lập hóa đơn nháp|
|2|Xem trước hóa đơn nháp (.pdf)|
|3|Phát hành hóa đơn|
|4|Xem file hóa đơn đã phát hành là .zip( chứa file .xml, .xsl)|

Danh sách được lưu trong bảng HR.HR\_EINVOICE\_METHOD:

`	`![A screenshot of a computer&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.003.png)
### <a name="_toc182235363"></a><a name="_toc182235484"></a>4.2 Chức năng ở mục OM:
Tên: SYS.OM\_INVOICE

Mô tả: hàm này sẽ lấy 2 thông số đầu vào là:

- header\_id ( kiểu dữ liệu varchar2) trong bảng OM.OM\_INVOICE\_HEADERS.
- Method\_id (kiểu dữ liệu varchar2)( ví dụ: ‘1’,’2’,...) theo mục 3. ở trên.

Cột hiện thị trạng thái khi sau khi gọi API: einvoice\_api\_status trong bảng OM.OM\_INVOICE\_HEADERS. (được lưu theo header\_id)

Cách sử dụng:  

![A screenshot of a computer code&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.004.png)

- Chỉ cần thay đổi header\_id thì hàm sẽ lấy thông tin hóa đơn của chức năng đó trong database và gửi lên hóa đơn điện tử viettel
- Lỗi có thể xảy ra (tốt nhất là liên lạc em để có thể xác định rõ vì form ko hiện ra) có các nguyên nhân sau: 
  - Mã số thuế của người mua sai( hóa đơn điện tử xác định mã số thuế của người mua theo mã số thuế thật)
  - Nếu hóa đơn không hiện như header\_id (56132) có thể là vì hóa đơn này đã phát hành nên không thể tạo thông tin hóa đơn nháp.

Cách tra cứu:

- đăng nhập vào tài khoản ở đường link sau đó truy cập ở mục hóa đơn chưa phát hành trong thư mục hóa đơn chưa phát hành.
- Nếu hóa đơn nháp được tạo ngày nào thì sẽ được lưu lúc đó

![A screenshot of a computer&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.005.png)

\
\
\

### <a name="_toc182235364"></a><a name="_toc182235485"></a>4.3 Chức năng ở mục AR:
Tên: SYS.AR\_INVOICE

Mô tả: hàm này sẽ lấy 2 thông số đầu vào là:

- header\_id ( kiểu dữ liệu varchar2) trong bảng AR.AR\_INVOICE\_HEADERS.
- Method\_id (kiểu dữ liệu varchar2)( ví dụ: ‘1’,’2’,...) theo mục 3. ở trên.

Cột hiện thị trạng thái khi sau khi gọi API: einvoice\_api\_status trong bảng AR.AR\_INVOICE\_HEADERS. (được lưu theo header\_id)

Cách sử dụng:  

- Theo ví dụ thì hàm sẽ được gọi như thế này: 

SYS.AR\_INVOICE(‘57565’,’’2’);

![A screenshot of a computer code&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.006.png)

\

### <a name="_toc182235365"></a><a name="_toc182235486"></a>4.4 Chức năng mục INV: 
(tình trạng: hoàn thành đa phần, vì chưa được quyết định về thuế và trạng thái thanh toán)

Tên: SYS.INV\_INVOICE

Mô tả: hàm này sẽ lấy 2 thông số đầu vào là:

- transaction\_header\_id ( kiểu dữ liệu varchar2) trong bảng INV.INV\_TRANSACTION\_HEADERS.
- Method\_id (kiểu dữ liệu varchar2)( ví dụ: ‘1’,’2’,...) theo mục 3. ở trên.

Cột hiện thị trạng thái khi sau khi gọi API: einvoice\_api\_status trong bảng INV.INV\_TRANSACTION\_HEADERS. (được lưu theo transaction\_header\_id)

Cách sử dụng:  

- Theo ví dụ thì hàm sẽ được gọi như thế này: 

SYS.INV\_INVOICE(‘274598’,’’2’);

![A screenshot of a computer code&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.007.png)

\

### <a name="_toc182235366"></a><a name="_toc182235487"></a>4.5 Chức năng mục PO: 
(tình trạng: hoàn thành đa phần, chưa quyết định thuế và trạng thái thanh toán)

Tên chức năng: SYS.PO\_INVOICE

Mô tả: hàm này sẽ lấy 2 thông số đầu vào là:

- transaction\_header\_id ( kiểu dữ liệu varchar2) trong bảng PO.PO\_RETURN\_HEADERS.
- Method\_id (kiểu dữ liệu varchar2)( ví dụ: ‘1’,’2’,...) theo mục 3. ở trên.

Cột hiện thị trạng thái sau khi gọi API: einvoice\_api\_status trong bảng INV.INV\_TRANSACTION\_HEADERS. (được lưu theo transaction\_header\_id)

Cách sử dụng:  

- Theo ví dụ thì hàm sẽ được gọi như thế này: 

SYS.PO\_INVOICE(‘274128’,’2’);

![A screenshot of a computer code&#x0A;&#x0A;Description automatically generated](Aspose.Words.9cc95a33-7bab-4f86-8b16-1f27a1d2e058.008.png)
### <a name="_toc182235367"></a><a name="_toc182235488"></a>4.6 Thông tin in hóa đơn:
Địa chỉ ip sử dụng: <http://123.31.12.59:10001/> ( tương ứng với đi tới thư mục E:\Report\)

Hóa đơn sẽ được lưu trong thư mục E:\Report\einvoice\_pdf\
1. ## Các bước cài đặt hóa đơn điện tử
Bước 1: Sử dụng java manager để load file java ở trong thư mục .. chọn file runOnOtherJDK.java ở thư mục src và runOnOtherJDK.class ở thư mục bin

Lưu ý: thư mục code nằm trong E:\FORMS\EINVOICE\workspace

Bước 2: mở Editor và tạo procedure cho 4 chức năng:

- OM\_INVOICE
- AR\_INVOICE
- PO\_INVOICE
- INV\_INVOICE

Thay thế các tên lần lượt vào câu lệnh sau:

CREATE OR REPLACE PROCEDURE SYS.AR\_INVOICE (HEADER\_ID VARCHAR2, METHOD\_ID VARCHAR2) 

` `AS LANGUAGE JAVA NAME 

'runOnOtherJDK.AR\_INVOICE(java.lang.String, java.lang.String)'

GRANT EXECUTE ON SYS.AR\_INVOICE TO HR 



