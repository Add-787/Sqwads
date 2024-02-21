using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using Sqwads.Domain.Common;

namespace Sqwads.Domain.Squads;

public class Squad : Entity
{
    [Required]
    public string Name { get; private set; }

    [DefaultValue(Formation.FourFourTwo)]
    public Formation Formation { get; private set; }
    

}
