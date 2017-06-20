using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entitites;
using Repository;

namespace Services.Implementations
{
    public class QrCodeService : IQrCodeService
    {

        private IUnitOfWork unitOfWork;

        public QrCodeService()
        {
            this.unitOfWork = new UnitOfWork();
        }

        public QrCodeService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public QrCode CreateQRCode(QrCode qrCode)
        {
            unitOfWork.QrCodeRepository.Insert(qrCode);
            unitOfWork.Save();
            return qrCode;
        }
    }
}
