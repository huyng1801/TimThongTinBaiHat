create table BaiHat(
MaBaiHat int identity(1,1) not null primary key,
TenBaiHat nvarchar(200) not null,
TenCaSy nvarchar(200) not null,
Link varchar(200) not null
);
INSERT INTO BaiHat (TenBaiHat, TenCaSy, Link) VALUES
('Bật Tình Yêu Lên', 'Tăng Duy Tân, Hòa Minzy', 'https://www.nhaccuatui.com/playlist/top-100-nhac-tre-hay-nhat-va.m3liaiy6vVsF.html?st=2https://www.link1.com'),
('Cô Gái Này Là Của Ai?', 'KxR, Nhi Nhi', 'https://www.nhaccuatui.com/playlist/top-100-nhac-tre-hay-nhat-va.m3liaiy6vVsF.html?st=7'),
('Bồng Bềnh Bồng Bềnh (The Heroes 2022)', 'Nam Em', 'https://www.nhaccuatui.com/playlist/top-100-nhac-tre-hay-nhat-va.m3liaiy6vVsF.html?st=28'),
('Khuất Lối', 'H-Kray', 'https://www.nhaccuatui.com/playlist/top-100-nhac-tre-hay-nhat-va.m3liaiy6vVsF.html?st=46'),
('Sao Cũng Được', 'Thành Đạt', 'https://www.nhaccuatui.com/playlist/top-100-nhac-tre-hay-nhat-va.m3liaiy6vVsF.html?st=74');
