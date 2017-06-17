using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Entitites;
using System.Web;
using System.IO;
using Utils;
using Repository;

namespace Services.Implementations
{
    public class EventService : IEventService
    {


        private IUnitOfWork unitOfWork;

        public EventService()
        {
            this.unitOfWork = new UnitOfWork();
        }
        public EventService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }
        public Event CreateEvent(Event newEvent, string imgStr,string imgName)
        {
            string imagePath = ImageHelper.saveImage(imgStr,imgName);
            newEvent.ImageUrl = imagePath;
            unitOfWork.EventRepository.Insert(newEvent);
            unitOfWork.Save();

            return newEvent;
        }


    }
}