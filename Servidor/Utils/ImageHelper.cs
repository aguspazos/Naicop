using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace Utils
{
    public class ImageHelper
    {
        public static string saveImage(string imgStr, string imgName)
        {
            String path = HttpContext.Current.Server.MapPath("~/ImageStorage");

            if (!System.IO.Directory.Exists(path))
            {
                System.IO.Directory.CreateDirectory(path);
            }
            string extension = getImageExtension(imgName);
            string imageName = HelperFunctions.RandomString(8);
            imageName = imageName + "."+extension;

            //set the image path
            string imgPath = Path.Combine(path, imageName);

            byte[] imageBytes = Convert.FromBase64String(imgStr);

            File.WriteAllBytes(imgPath, imageBytes);

            return "ImageStorage/"+imageName;
        }

        private static string getImageExtension(string imgName)
        {
            string[] nameData = imgName.Split('.');
            if (nameData.Length > 0)
                return nameData.Last();

            return ".jpg";
        }
    }
}
