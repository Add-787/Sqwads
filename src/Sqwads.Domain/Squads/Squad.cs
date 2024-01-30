using System.ComponentModel.DataAnnotations;
using Sqwads.Domain.Common;

namespace Sqwads.Domain.Squads;

public class Squad : Entity
{
    [Required]
    public string Name { get; private set; }

    [Required]
    public Formation Formation { get; private set; }
    

}
